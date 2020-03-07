package cn.wgn.website.service.impl;

import cn.wgn.website.dto.manage.*;
import cn.wgn.website.dto.utils.IpRegion;
import cn.wgn.website.entity.NovelEntity;
import cn.wgn.website.entity.VistorEntity;
import cn.wgn.website.enums.NovelTypeEnum;
import cn.wgn.website.enums.StateEnum;
import cn.wgn.website.mapper.NovelMapper;
import cn.wgn.website.mapper.UserMapper;
import cn.wgn.website.mapper.VistorMapper;
import cn.wgn.website.service.IManageService;
import cn.wgn.website.utils.*;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.abel533.echarts.AxisPointer;
import com.github.abel533.echarts.Grid;
import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.style.ItemStyle;
import com.github.abel533.echarts.style.itemstyle.Normal;
import com.google.common.base.Strings;
import org.markdownj.MarkdownProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/18 17:37
 */
@Service("manageService")
public class ManageServiceImpl extends BaseServiceImpl implements IManageService {
    @Autowired
    private NovelMapper novelMapper;
    @Autowired
    private HttpUtil httpUtil;
    @Autowired
    private WordUtil wordUtil;
    @Autowired
    private CosClientUtil cosClientUtil;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IpUtil ipUtil;
    @Autowired
    private VistorMapper vistorMapper;

    /**
     * 获取首页信息
     *
     * @return
     */
    @Override
    public HomeInfo getHomeInfo() {
        HomeInfo homeInfo = userMapper.getHomeInfo(getUserData().getId());
        homeInfo.setLastIp(ipUtil.int2ip(Integer.parseInt(homeInfo.getLastIp())));
        IpRegion ipRegion = ipUtil.getIpRegion(homeInfo.getLastIp());
        String lastAdd = ipRegion.getCountry() + ipRegion.getArea() + ipRegion.getProvince() + ipRegion.getCity()
                + (Strings.isNullOrEmpty(ipRegion.getIsp()) ? "" : ("," + ipRegion.getIsp()));
        homeInfo.setLastAdd(lastAdd);
        homeInfo.setWeekChart(getHomeChart());
        return homeInfo;
    }

    /**
     * 新增小说
     *
     * @param novelDto
     * @param novelTypeEnum
     * @return
     */
    @Override
    public Object addNovel(NovelDto novelDto, NovelTypeEnum novelTypeEnum) {
        if (novelDto.getId() != null && novelDto.getId() > 0) {
            NovelEntity entity = novelMapper.selectById(novelDto.getId());
            if (entity == null
                    || !novelTypeEnum.toString().equals(entity.getNovelType())
                    || !getUserData().getAccount().equals(entity.getNovelAuthor())) {
                return "您是更新[" + novelTypeEnum.toString() + "]小说吗？为什么我找不到呢？";
            }
            entity.setNovelAuthor(getUserData().getAccount())
                    .setNovelTitle(novelDto.getNovelTitle())
                    .setNovelContent(novelDto.getNovelContent())
                    .setNovelType(novelTypeEnum.toString())
                    .setUpdateTm(LocalDateTime.now())
                    .setState(StateEnum.NORMAL.getValue());
            novelMapper.updateById(entity);
            return entity.getId();
        } else {
            NovelEntity entity = new NovelEntity();
            BeanUtils.copyProperties(novelDto, entity);
            entity.setNovelAuthor(getUserData().getAccount())
                    .setCreateTm(LocalDateTime.now())
                    .setNovelType(novelTypeEnum.toString())
                    .setState(StateEnum.NORMAL.getValue());
            novelMapper.insert(entity);
            return entity.getId();
        }
    }

    /**
     * 查看小说列表
     *
     * @param dto
     * @return
     */
    @Override
//    @Cacheable(value = "novelList", key = "#dto.toString()+#dto.pageIndex+'_'+#dto.pageSize")
    public Page novelList(NovelQueryDto dto) {
        Page page = new Page(dto.getPageIndex(), dto.getPageSize());
        Page novelPage = novelMapper.selectPage(page, novelListQw(dto));

        List<NovelEntity> records = novelPage.getRecords();
        List<Novel> result = new ArrayList<>();
        Novel novel;
        for (NovelEntity entity : records) {
            novel = new Novel();
            BeanUtils.copyProperties(entity, novel);
            novel.setNovelContent(WebSiteUtil.cutContent(entity.getNovelContent(), 100));
            result.add(novel);
        }
        novelPage.setRecords(result);
        return novelPage;
    }

    /**
     * 查看小说列表（Excel）
     *
     * @param dto
     * @return
     */
    @Override
//    @Cacheable(value = "novelListExcel", key = "#dto.toString()+#dto.pageIndex+'_'+#dto.pageSize")
    public List<Novel> novelListExcel(NovelQueryDto dto) {
        List<NovelEntity> list = novelMapper.selectList(novelListQw(dto));
        List<Novel> result = new ArrayList<>();
        Novel novel;
        for (NovelEntity entity : list) {
            novel = new Novel();
            BeanUtils.copyProperties(entity, novel);
            novel.setNovelContent(WebSiteUtil.cutContent(entity.getNovelContent(), 100));
            result.add(novel);
        }

        LOG.info("用户[" + getUserData().getAccount() + "]下载 Novel List Excel，查询条件[" + dto.toString() + "]");
        return result;
    }

    /**
     * 查看小说列表（QueryWrapper）
     *
     * @param dto
     * @return
     */
    private QueryWrapper<NovelEntity> novelListQw(NovelQueryDto dto) {
        QueryWrapper<NovelEntity> qw = new QueryWrapper<NovelEntity>();
        if (getUserData().getAccount() != null) {
            qw.lambda().like(NovelEntity::getNovelAuthor, getUserData().getAccount());
        }
        if (!Strings.isNullOrEmpty(dto.getNovelTitle())) {
            qw.lambda().like(NovelEntity::getNovelTitle, dto.getNovelTitle());
        }
        if (!Strings.isNullOrEmpty(dto.getNovelType())) {
            qw.lambda().eq(NovelEntity::getNovelType, dto.getNovelType());
        }
        if (dto.getCreateTm1() != null) {
            qw.lambda().gt(NovelEntity::getCreateTm, dto.getCreateTm1());
        }
        if (dto.getCreateTm2() != null) {
            qw.lambda().lt(NovelEntity::getCreateTm, dto.getCreateTm2());
        }
        if (dto.getUpdateTm1() != null) {
            qw.lambda().gt(NovelEntity::getCreateTm, dto.getUpdateTm1());
        }
        if (dto.getUpdateTm2() != null) {
            qw.lambda().lt(NovelEntity::getCreateTm, dto.getUpdateTm2());
        }
        qw.lambda().eq(NovelEntity::getState, StateEnum.NORMAL.getValue());
        if (!Strings.isNullOrEmpty(dto.getOrderBy())) {
            String[] s = dto.getOrderBy().split(" ");
            qw.orderBy(true, "ASC".equalsIgnoreCase(s[1]), s[0]);
        } else {
            qw.lambda().orderByDesc(NovelEntity::getId);
        }
        return qw;
    }

    /**
     * 查看小说
     *
     * @param novelId
     * @return
     */
    @Override
    public NovelEntity novelDetail(Integer novelId) {
        NovelEntity entity = novelMapper.selectById(novelId);
        if (entity != null
                && StateEnum.NORMAL.getValue().equals(entity.getState())
                && getUserData().getAccount().equals(entity.getNovelAuthor())) {
            return entity;
        } else {
            return null;
        }
    }

    /**
     * 删除小说(逻辑删除)
     *
     * @param novelId
     * @return
     */
    @Override
    public String novelDelete(Integer novelId) {
        NovelEntity entity = novelMapper.selectById(novelId);
        String data;
        if (entity == null) {
            data = "Novel ID = [" + novelId + "]不存在!";
        } else if (!StateEnum.NORMAL.getValue().equals(entity.getState())) {
            data = "Novel ID = [" + novelId + "]已被删除!";
        } else if (!getUserData().getAccount().equals(entity.getNovelAuthor())) {
            data = "不是作者无权删除!";
        } else {
            entity.setState(StateEnum.DELETE.getValue());
            int res = novelMapper.updateById(entity);
            if (res == 1) {
                data = "1";
            } else {
                data = "操作失败";
            }
        }
        return data;
    }

    /**
     * 下载文档
     *
     * @param id
     * @return
     */
    @Override
    public String downloadDoc(Integer id) {
        NovelEntity novelEntity = novelMapper.selectById(id);
        if (novelEntity == null) {
            return null;
        }
        String htmlBody = novelEntity.getNovelContent();
        if (NovelTypeEnum.Markdown.toString().equals(novelEntity.getNovelType())) {
            MarkdownProcessor markdownProcessor = new MarkdownProcessor();
            htmlBody = markdownProcessor.markdown(htmlBody);
        }

        String filePath = wordUtil.html2Word(htmlBody);
        String url = cosClientUtil.uploadFile2Cos(new File(filePath), "noveldoc");

        LOG.info("用户[" + getUserData().getAccount() + "]下载 Novel，ID=[" + id + "]，URL=[" + url + "]");
        return url;
    }

    private String getHomeChart() {
        List<VistorEntity> vistorEntityList = vistorMapper.selectList(
                new QueryWrapper<VistorEntity>().lambda()
                        .between(VistorEntity::getTm, LocalDateTime.of(LocalDate.now(), LocalTime.MIN).minusDays(6), LocalDateTime.now())
        );

        // 日期坐标轴
        String[] yAxis = new String[7];
        for (int i = 0; i < 7; i++) {
            yAxis[i] = LocalDate.now().minusDays(6 - i).toString();
        }

        // LinkedHashMap<接口名称，LinkedHashMap<日期，数量>>
        LinkedHashMap<String, LinkedHashMap<String, Integer>> data = new LinkedHashMap<>();
        for (VistorEntity e : vistorEntityList) {
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
        option.title().text("最近七天接口调用统计").subtext("wuguangnuo.cn").left("3%");
        option.tooltip().trigger(Trigger.axis).axisPointer(new AxisPointer().type(PointerType.shadow));
        option.legend(new Legend().top("8%").data(data.keySet().toArray()));
        option.toolbox().show(true).right("3%").feature(Tool.mark, Tool.dataView,
                new MagicType(Magic.line, Magic.bar).show(true), Tool.restore, Tool.saveAsImage);
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
}
