package cn.wgn.website.service;

import java.time.LocalDateTime;

/**
 * Redis 缓存服务
 *
 * @author WuGuangNuo
 * @date Created in 2020/3/8 13:11
 */
public interface ICacheService {
    /**
     * 获取菜单列表
     *
     * @return
     */
    String getMenuJson();

    /**
     * 获取首页接口访问图表
     *
     * @return
     */
    String getHomeChart();

    /**
     * 访客类型图表
     * 默认最近三月
     * wuguangnuo.cn
     *
     * @return
     */
    String getVistorChart(LocalDateTime dateTime1, LocalDateTime dateTime2);

    /**
     * 受访页面图表
     * 默认最近七天
     * wuguangnuo.cn
     *
     * @return
     */
    String getLinkChart(LocalDateTime dateTime1, LocalDateTime dateTime2);

    /**
     * 操作系统图表
     * 默认最近七天
     * wuguangnuo.cn
     *
     * @return
     */
    String getSystemChart(LocalDateTime dateTime1, LocalDateTime dateTime2);

    /**
     * 客户端图表
     * 默认最近七天
     * wuguangnuo.cn
     *
     * @return
     */
    String getBrowserChart(LocalDateTime dateTime1, LocalDateTime dateTime2);
}
