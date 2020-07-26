package cn.wgn.framework.web.service;

import cn.wgn.framework.web.entity.VisitorEntity;
import cn.wgn.website.sys.dto.HomeInfo;

import java.util.HashMap;

/**
 * <p>
 * 访客统计 服务类
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-27
 */
public interface IVisitorService extends IBaseService<VisitorEntity> {
    /**
     * 获取访客图表
     *
     * @param query type = "图表类型"
     *              date1 = "时间1"
     *              date2 = "时间2"
     * @return echarts string
     */
    String vistorChart(HashMap<String, String> query);

    /**
     * 获取首页信息
     *
     * @return
     */
    HomeInfo getHomeInfo();
}
