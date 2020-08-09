package cn.wgn.framework.web.service.impl;

import cn.wgn.framework.utils.DateUtil;
import cn.wgn.framework.utils.ip.IpRegion;
import cn.wgn.framework.utils.ip.IpUtil;
import cn.wgn.framework.web.entity.VisitorEntity;
import cn.wgn.framework.web.mapper.VisitorMapper;
import cn.wgn.framework.web.service.ICacheService;
import cn.wgn.framework.web.service.IVisitorService;
import cn.wgn.framework.web.domain.HomeInfo;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * <p>
 * 访客统计 服务实现类
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-27
 */
@Service
public class VisitorServiceImpl extends BaseServiceImpl<VisitorMapper, VisitorEntity> implements IVisitorService {
    @Autowired
    private ICacheService cacheService;
    @Autowired
    private VisitorMapper visitorMapper;
    @Autowired
    private IpUtil ipUtil;

    /**
     * 获取访客图表
     *
     * @param query type = "图表类型"
     *              date1 = "时间1"
     *              date2 = "时间2"
     * @return echarts string
     */
    @Override
    public String vistorChart(HashMap<String, String> query) {
        // 默认最近七天
        LocalDate date1 = query.containsKey("date1") ? LocalDate.parse(query.get("date1"), DateTimeFormatter.ofPattern(DateUtil.DATE_PATTERN)) : LocalDate.now().minusDays(6);
        LocalDate date2 = query.containsKey("date2") ? LocalDate.parse(query.get("date2"), DateTimeFormatter.ofPattern(DateUtil.DATE_PATTERN)) : LocalDate.now();
        // 时间排序
        if (date1.isAfter(date2)) {
            LocalDate tmpDate = date1;
            date1 = date2;
            date2 = tmpDate;
        }
        LocalDateTime dateTime1 = LocalDateTime.of(date1, LocalTime.MIN);
        LocalDateTime dateTime2 = LocalDateTime.of(date2, LocalTime.MAX);

        if (!query.containsKey("type")) {
            return "chart type empty!";
        }

        String result;
        switch (query.get("type")) {
            case "vistor":
                result = cacheService.getVistorChart(LocalDateTime.of(LocalDate.now().minusMonths(3), LocalTime.MIN), LocalDateTime.now());
                break;
            case "link":
                result = cacheService.getLinkChart(dateTime1, dateTime2);
                break;
            case "system":
                result = cacheService.getSystemChart(dateTime1, dateTime2);
                break;
            case "browser":
                result = cacheService.getBrowserChart(dateTime1, dateTime2);
                break;
            default:
                result = "chart type error!";
                break;
        }

        return result;
    }

    /**
     * 获取首页信息
     *
     * @return
     */
    @Override
    public HomeInfo getHomeInfo() {
        HomeInfo homeInfo = visitorMapper.getHomeInfo(getUserData().getId());
        homeInfo.setLastIp(IpUtil.int2ip(Integer.parseInt(homeInfo.getLastIp())));
        IpRegion ipRegion = ipUtil.getIpRegion(homeInfo.getLastIp());
        String lastAdd = ipRegion.getCountry() + ipRegion.getArea() + ipRegion.getProvince() + ipRegion.getCity()
                + (Strings.isNullOrEmpty(ipRegion.getIsp()) ? "" : ("," + ipRegion.getIsp()));
        homeInfo.setLastAdd(lastAdd);
        homeInfo.setWeekChart(cacheService.getHomeChart());
        return homeInfo;
    }
}
