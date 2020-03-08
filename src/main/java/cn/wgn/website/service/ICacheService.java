package cn.wgn.website.service;

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
}
