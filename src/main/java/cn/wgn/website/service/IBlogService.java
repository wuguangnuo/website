package cn.wgn.website.service;

import cn.wgn.framework.web.service.IBaseService;
import cn.wgn.website.entity.BlogEntity;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 諾的博客 服务类
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-27
 */
public interface IBlogService extends IBaseService<BlogEntity> {

    /**
     * 获取博文列表
     *
     * @param query 查询条件
     *              type = "文章类型"
     *              keyword = "关键词"
     * @return
     */
    List<BlogEntity> queryBlogList(HashMap<String, String> query);

    /**
     * 获取博文列表2(临时)
     *
     * @param query 查询条件
     *              type = "文章类型"
     *              keyword = "关键词"
     * @return pageInfo = "分页信息", blogList = "博文列表"
     */
    HashMap queryBlogList2(HashMap<String, String> query);

    /**
     * 自定义SQL
     *
     * @param sql
     * @return
     */
    String sql(String sql);
}
