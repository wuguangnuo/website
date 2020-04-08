package cn.wgn.website.service.impl;

import cn.wgn.website.entity.CallEntity;
import cn.wgn.website.entity.Dictionary;
import cn.wgn.website.entity.VistorEntity;
import cn.wgn.website.mapper.CallMapper;
import cn.wgn.website.mapper.DictionaryMapper;
import cn.wgn.website.mapper.VistorMapper;
import cn.wgn.website.service.ICacheService;
import cn.wgn.website.utils.DateUtil;
import cn.wgn.website.utils.FileUtil;
import cn.wgn.website.utils.WebSiteUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.abel533.echarts.*;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.series.Series;
import com.github.abel533.echarts.style.ItemStyle;
import com.github.abel533.echarts.style.itemstyle.Emphasis;
import com.github.abel533.echarts.style.itemstyle.Normal;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Redis 缓存服务
 *
 * @author WuGuangNuo
 * @date Created in 2020/3/8 13:11
 */
@Service("cacheService")
public class CacheServiceImpl implements ICacheService {
    @Autowired
    private CallMapper callMapper;
    @Autowired
    private VistorMapper vistorMapper;
    @Autowired
    private DictionaryMapper dictionaryMapper;
    @Autowired
    private FileUtil fileUtil;

    /**
     * 获取菜单列表
     * {
     * "code":"所需权限",
     * "icon":"图标",
     * "url":"相对链接",
     * "name":"菜单名称"
     * }
     *
     * @return
     */
    @Override
    @Cacheable(value = "MenuJson")
    public String getMenuJson() {
        return fileUtil.getMenuJson();
    }

    /**
     * 获取首页接口访问图表
     *
     * @return
     */
    @Override
    @Cacheable(value = "HomeChart")
    public String getHomeChart() {
        List<CallEntity> callEntityList = callMapper.selectList(
                new QueryWrapper<CallEntity>().lambda()
                        .select(CallEntity::getLk, CallEntity::getTm)
                        .between(CallEntity::getTm, LocalDateTime.of(LocalDate.now(), LocalTime.MIN).minusDays(6), LocalDateTime.now())
        );

        // 日期坐标轴
        String[] yAxis = new String[7];
        for (int i = 0; i < yAxis.length; i++) {
            yAxis[i] = LocalDate.now().minusDays(yAxis.length - 1 - i).toString();
        }

        // LinkedHashMap<接口名称，LinkedHashMap<日期，数量>>
        LinkedHashMap<String, LinkedHashMap<String, Integer>> data = new LinkedHashMap<>();
        for (CallEntity e : callEntityList) {
            String date = e.getTm().toLocalDate().toString();
            if (data.containsKey(e.getLk())) {
                int num = data.get(e.getLk()).getOrDefault(date, 0) + 1;
                data.get(e.getLk()).put(date, num);
            } else {
                LinkedHashMap<String, Integer> temp = new LinkedHashMap<>();
                for (String yAxi : yAxis) {
                    temp.put(yAxi, 0);
                }
                temp.put(date, 1);
                data.put(e.getLk(), temp);
            }
        }

        Option option = new Option();
        option.title().text("最近七天接口调用统计").subtext("api.wuguangnuo.cn 数据更新时间:" + DateUtil.dateFormat(null, DateUtil.MINUTE_PATTERN)).left("3%");
        option.tooltip().trigger(Trigger.axis).axisPointer(new AxisPointer().type(PointerType.shadow));
        option.legend(new Legend().top("8%").data(data.keySet().toArray()));
        option.toolbox().show(true).right("3%").feature(Tool.mark, Tool.dataView, Tool.restore, Tool.saveAsImage);
        option.calculable(true);
        option.xAxis(new CategoryAxis().data(yAxis));
        option.yAxis(new ValueAxis());
        option.grid(new Grid().top("20%").left("3%").right("3%").bottom("3%").containLabel(true));

        for (String key : data.keySet()) {
            option.series(new Bar(key)
                    .data(data.get(key).values().toArray())
                    .stack("总量")
                    .label(new ItemStyle().normal(new Normal().show(true).position(Position.inside))));
        }
        return JSON.toJSONString(option);
    }

    /**
     * 访客类型图表
     * 默认最近三月
     * 靠字典表中的正则匹配
     * wuguangnuo.cn
     *
     * @param dateTime1
     * @param dateTime2
     * @return
     */
    @Override
    @Cacheable(value = "VistorChart", key = "#dateTime1.toLocalDate()+'_'+#dateTime2.toLocalDate()")
    public String getVistorChart(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        List<VistorEntity> vistorEntityList = vistorMapper.selectList(
                new QueryWrapper<VistorEntity>().lambda()
                        .select(VistorEntity::getAg, VistorEntity::getTm)
                        .between(VistorEntity::getTm, dateTime1, dateTime2)
        );

        // 日期坐标轴
        String[] yAxis = new String[(int) Duration.between(dateTime1, dateTime2).toDays() + 1];
        for (int i = 0; i < yAxis.length; i++) {
            yAxis[i] = LocalDate.now().minusDays(yAxis.length - 1 - i).toString();
        }

        // 爬虫折线类型
        List<Dictionary> dictionaryList = dictionaryMapper.selectList(
                new QueryWrapper<Dictionary>().lambda()
                        .eq(Dictionary::getGroupKey, "spider_type")
        );
//        List<String> vistorShow = dictionaryList.stream().map(
//                Dictionary::getCodeNote
//        ).collect(Collectors.toList());
        // all -> 全部
        Map<String, String> vistorShow = new HashMap<>();
        for (Dictionary d : dictionaryList) {
            vistorShow.put(d.getCodeValue(), d.getCodeNote());
        }

        // LinkedHashMap<接口名称，LinkedHashMap<日期，数量>>
        LinkedHashMap<String, LinkedHashMap<String, Integer>> data = new LinkedHashMap<>();

        LinkedHashMap<String, Integer> zeroLine = new LinkedHashMap<>();
        for (String d : yAxis) {
            zeroLine.put(d, 0);
        }

        // 初始化接口名称
        data.put("总访问量", (LinkedHashMap<String, Integer>) zeroLine.clone());
        data.put("访客", (LinkedHashMap<String, Integer>) zeroLine.clone());
        for (String key : vistorShow.keySet()) {
            data.put(vistorShow.get(key), (LinkedHashMap<String, Integer>) zeroLine.clone());
        }
        data.put("其他流量", (LinkedHashMap<String, Integer>) zeroLine.clone());

        for (VistorEntity e : vistorEntityList) {
            String date = e.getTm().toLocalDate().toString();
            String ag = e.getAg();
            data.get("总访问量").put(date, data.get("总访问量").get(date) + 1);
            if (Strings.isNullOrEmpty(ag)) {
                continue;
            }

            // 正则检测蜘蛛
            if (ag.contains("spider") || ag.contains("bot")) {
                // 匹配字典中的蜘蛛类型
                boolean isNormalSpider = false;
                for (String key : vistorShow.keySet()) {
                    if (ag.contains(key)) {
                        int num = data.get(vistorShow.get(key)).get(date) + 1;
                        data.get(vistorShow.get(key)).put(date, num);
                        isNormalSpider = true;
                        break;
                    }
                }
                if (!isNormalSpider) {
                    data.get("其他流量").put(date, data.get("其他流量").get(date) + 1);
                }
            } else {
                data.get("访客").put(date, data.get("访客").get(date) + 1);
            }
        }

        Option option = new Option();
        option.title().text("访客类型统计图").subtext("api.wuguangnuo.cn 数据更新时间:" + DateUtil.dateFormat(null, DateUtil.MINUTE_PATTERN)).left("3%");
        option.tooltip().trigger(Trigger.axis);
        option.legend(new Legend().data(data.keySet().toArray()));
        // yaxisindex none
        option.toolbox().show(true).right("3%").feature(new DataZoom().yAxisIndex("none"), Tool.restore, Tool.dataView, Tool.saveAsImage);

        List<DataZoom> dataZoomList = new ArrayList<>();
        dataZoomList.add(new DataZoom().startValue(LocalDate.now().minusMonths(1)));
        dataZoomList.add(new DataZoom().type(DataZoomType.inside));
        option.setDataZoom(dataZoomList);
        option.xAxis(new CategoryAxis().data(yAxis).boundaryGap(false).type(AxisType.category));
        option.yAxis(new ValueAxis().type(AxisType.value));
        for (String key : data.keySet()) {
            option.series(new Line(key)
                    .data(data.get(key).values().toArray()));
        }

        return JSON.toJSONString(option);
    }

    /**
     * 受访页面图表
     * 默认最近七天
     * 网络蜘蛛字典表正则匹配
     * wuguangnuo.cn
     *
     * @param dateTime1
     * @param dateTime2
     * @return
     */
    @Override
    @Cacheable(value = "LinkChart", key = "#dateTime1.toLocalDate()+'_'+#dateTime2.toLocalDate()")
    public String getLinkChart(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        List<VistorEntity> vistorEntityList = vistorMapper.selectList(
                new QueryWrapper<VistorEntity>().lambda()
                        .select(VistorEntity::getLk, VistorEntity::getAg)
                        .between(VistorEntity::getTm, dateTime1, dateTime2)
        );

        // 受访页面to
        List<Dictionary> dictionaryList = dictionaryMapper.selectList(
                new QueryWrapper<Dictionary>().lambda()
                        .select(Dictionary::getCodeValue, Dictionary::getCodeNote)
                        .eq(Dictionary::getGroupKey, "link_type")
        );
//        String[] yAxis = dictionaryList.stream().map(
//                Dictionary::getCodeNote
//        ).toArray(String[]::new);
        List<String> yAxisList = dictionaryList.stream().map(
                Dictionary::getCodeNote
        ).collect(Collectors.toList());
        yAxisList.add("其他页面");
        String[] yAxis = new String[yAxisList.size()];
        yAxisList.toArray(yAxis);

        // LinkedHashMap<折线类型，LinkedHashMap<访问页面，数量>>
        LinkedHashMap<String, LinkedHashMap<String, Integer>> data = new LinkedHashMap<>();

        LinkedHashMap<String, Integer> zeroLine = new LinkedHashMap<>();
        for (String d : yAxis) {
            zeroLine.put(d, 0);
        }

        // 初始化接口名称
        data.put("总访问量", (LinkedHashMap<String, Integer>) zeroLine.clone());
        data.put("访客", (LinkedHashMap<String, Integer>) zeroLine.clone());
        data.put("爬虫", (LinkedHashMap<String, Integer>) zeroLine.clone());

        for (VistorEntity e : vistorEntityList) {
            // 横坐标类型 主页-后台-日记
            String linkType = "其他页面";
            String ag = e.getAg();
            String lk = e.getLk();
            if (Strings.isNullOrEmpty(ag) || Strings.isNullOrEmpty(lk)) {
                continue;
            }
            for (Dictionary d : dictionaryList) {
                if (Pattern.matches(d.getCodeValue(), lk)) {
                    linkType = d.getCodeNote();
                    break;
                }
            }

            data.get("总访问量").put(linkType, data.get("总访问量").getOrDefault(linkType, 0) + 1);

            // 正则检测蜘蛛
            if (ag.contains("spider") || ag.contains("bot")) {
                data.get("爬虫").put(linkType, data.get("爬虫").getOrDefault(linkType, 0) + 1);
            } else {
                data.get("访客").put(linkType, data.get("访客").getOrDefault(linkType, 0) + 1);
            }
        }

        Option option = new Option();
        option.title().text("受访页面统计图").subtext("api.wuguangnuo.cn 数据更新时间:" + DateUtil.dateFormat(null, DateUtil.MINUTE_PATTERN)).left("3%");
        option.tooltip().trigger(Trigger.axis).axisPointer(new AxisPointer().type(PointerType.cross));
        option.legend(new Legend().data(data.keySet().toArray()));
        option.toolbox().show(true).right("3%").feature(
                Tool.dataView,
                Tool.magicType,
                Tool.restore,
                Tool.saveAsImage
        );
        option.xAxis(new CategoryAxis().data(yAxis).type(AxisType.category));
        option.yAxis(new ValueAxis().type(AxisType.value));
        option.series(new Line("总访问量")
                .data(data.get("总访问量").values().toArray()));
        option.series(new Bar("访客")
                .data(data.get("访客").values().toArray()));
        option.series(new Bar("爬虫")
                .data(data.get("爬虫").values().toArray()));

        String result = JSON.toJSONString(option);
        // 打补丁，增加阴影
        return result.replace("\"xAxis\":[{", "\"xAxis\":[{\"axisPointer\":{\"type\":\"shadow\"},");
    }

    /**
     * 操作系统图表
     * 默认最近七天
     * 本地正则匹配，显示哪些在字典表匹配
     * wuguangnuo.cn
     *
     * @param dateTime1
     * @param dateTime2
     * @return
     */
    @Override
    @Cacheable(value = "SystemChart", key = "#dateTime1.toLocalDate()+'_'+#dateTime2.toLocalDate()")
    public String getSystemChart(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        List<VistorEntity> vistorEntityList = vistorMapper.selectList(
                new QueryWrapper<VistorEntity>().lambda()
                        .select(VistorEntity::getAg)
                        .between(VistorEntity::getTm, dateTime1, dateTime2)
        );

        // 系统列表
        List<Dictionary> dictionaryList = dictionaryMapper.selectList(
                new QueryWrapper<Dictionary>().lambda()
                        .select(Dictionary::getCodeNote)
                        .eq(Dictionary::getGroupKey, "system_type")
        );
        List<String> systemList = dictionaryList.stream().map(
                Dictionary::getCodeNote
        ).collect(Collectors.toList());
        systemList.add("others");

        // LinkedHashMap<系统类型, 数量>
        LinkedHashMap<String, Integer> data = new LinkedHashMap<>();
        for (String k : systemList) {
            data.put(k, 0);
        }
        for (VistorEntity e : vistorEntityList) {
            String key = WebSiteUtil.getSystem(e == null ? null : e.getAg(), systemList);
            data.put(key, data.get(key) + 1);
        }

        Option option = new Option();
        option.title().text("操作系统统计图").subtext("api.wuguangnuo.cn 数据更新时间:" + DateUtil.dateFormat(null, DateUtil.MINUTE_PATTERN)).left("3%");
        option.tooltip().trigger(Trigger.item).formatter("{a}<br />{b}：{c}({d}%)");
        option.legend(new Legend().data(data.keySet().toArray()).right("5%").orient(Orient.vertical));
        option.toolbox().show(true).right("5%").bottom(0).feature(
                Tool.dataView,
                Tool.restore,
                Tool.saveAsImage
        );

        Map[] maps = new HashMap[data.size()];
        int i = 0;
        for (String key : data.keySet()) {
            Map tmpMap = new HashMap();
            tmpMap.put("name", key);
            tmpMap.put("value", data.get(key));
            maps[i] = tmpMap;
            i++;
        }
        option.series((Series) new Pie("操作系统")
                .itemStyle(new ItemStyle().emphasis(new Emphasis().shadowBlur(10).shadowColor("rgba(0,0,0,0.5)").shadowOffsetX(0)))
                .data(maps)
        );

        return JSON.toJSONString(option);
    }

    /**
     * 客户端图表
     * 默认最近七天
     * 本地正则匹配，显示哪些在字典表匹配
     * wuguangnuo.cn
     *
     * @param dateTime1
     * @param dateTime2
     * @return
     */
    @Override
    @Cacheable(value = "BrowserChart", key = "#dateTime1.toLocalDate()+'_'+#dateTime2.toLocalDate()")
    public String getBrowserChart(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        List<VistorEntity> vistorEntityList = vistorMapper.selectList(
                new QueryWrapper<VistorEntity>().lambda()
                        .select(VistorEntity::getAg)
                        .between(VistorEntity::getTm, dateTime1, dateTime2)
        );

        // 客户端列表
        List<Dictionary> dictionaryList = dictionaryMapper.selectList(
                new QueryWrapper<Dictionary>().lambda()
                        .select(Dictionary::getCodeNote)
                        .eq(Dictionary::getGroupKey, "browser_type")
        );
        List<String> browserList = dictionaryList.stream().map(
                Dictionary::getCodeNote
        ).collect(Collectors.toList());
        browserList.add("others");

        // LinkedHashMap<客户端类型, 数量>
        LinkedHashMap<String, Integer> data = new LinkedHashMap<>();
        for (String k : browserList) {
            data.put(k, 0);
        }
        for (VistorEntity e : vistorEntityList) {
            String key = WebSiteUtil.getBrowser(e == null ? null : e.getAg(), browserList);
            data.put(key, data.get(key) + 1);
        }

        Option option = new Option();
        option.title().text("用户客户端统计图").subtext("api.wuguangnuo.cn 数据更新时间:" + DateUtil.dateFormat(null, DateUtil.MINUTE_PATTERN)).left("3%");
        option.tooltip().trigger(Trigger.item).formatter("{a}<br />{b}：{c}({d}%)");
        option.legend(new Legend().data(data.keySet().toArray()).right("5%").orient(Orient.vertical));
        option.toolbox().show(true).right("5%").bottom(0).feature(
                Tool.dataView,
                Tool.restore,
                Tool.saveAsImage
        );

        Map[] maps = new HashMap[data.size()];
        int i = 0;
        for (String key : data.keySet()) {
            Map tmpMap = new HashMap();
            tmpMap.put("name", key);
            tmpMap.put("value", data.get(key));
            maps[i] = tmpMap;
            i++;
        }
        option.series((Series) new Pie("客户端")
                .itemStyle(new ItemStyle().emphasis(new Emphasis().shadowBlur(10).shadowColor("rgba(0,0,0,0.5)").shadowOffsetX(0)))
                .data(maps)
        );

        return JSON.toJSONString(option);
    }
}
