package cn.wgn.website.sys.service.impl;

import cn.wgn.framework.web.service.impl.BaseServiceImpl;
import cn.wgn.framework.constant.MagicValue;
import cn.wgn.website.sys.entity.BlogEntity;
import cn.wgn.website.sys.mapper.BlogMapper;
import cn.wgn.website.sys.service.IBlogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 諾的博客 服务实现类
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-27
 */
@Service
public class BlogServiceImpl extends BaseServiceImpl<BlogMapper, BlogEntity> implements IBlogService {
    @Autowired
    private BlogMapper blogMapper;

    /**
     * 获取博文列表
     *
     * @param query 查询条件
     *              type = "文章类型"
     *              keyword = "关键词"
     * @return
     */
    @Override
    public List<BlogEntity> queryBlogList(HashMap<String, String> query) {
        QueryWrapper<BlogEntity> qw = new QueryWrapper<>();
        qw.select("id", "post_title", "post_author", "post_type",
                "substr(post_content,1,300) as post_content", "post_date", "post_from", "post_link",
                "create_time", "create_by_name", "modified_time", "modified_by_name");
        if (query.containsKey(MagicValue.TYPE)) {
            qw.lambda().like(BlogEntity::getPostType, query.get(MagicValue.TYPE));
        }
        if (query.containsKey(MagicValue.KEYWORD)) {
            String keyword = query.get(MagicValue.KEYWORD);
            qw.lambda().like(BlogEntity::getPostTitle, keyword)
                    .or().like(BlogEntity::getPostAuthor, keyword)
                    .or().like(BlogEntity::getPostType, keyword)
                    .or().like(BlogEntity::getPostFrom, keyword);
        }
        return this.list(qw);
    }

    /**
     * 获取博文列表2(临时)
     *
     * @param query 查询条件
     *              type = "文章类型"
     *              keyword = "关键词"
     * @return pageInfo = "分页信息", blogList = "博文列表"
     */
    @Override
    public HashMap queryBlogList2(HashMap<String, String> query) {
        List<BlogEntity> res = this.queryBlogList(query);
        PageInfo<BlogEntity> blogList = new PageInfo<>(res);

        // 本页，最后一页
        long c = blogList.getPageNum();
        long e = blogList.getPages();

        StringBuilder sb = new StringBuilder();
        sb.append("<div>");
        sb.append("<a class='first btn" + (c == 1 ? " disabled'" : "' to='?pageIndex=1'") + ">首页</a>");
        sb.append("<a class='prev btn" + (c == 1 ? " disabled'" : "' to='?pageIndex=" + (c - 1) + "'") + ">上一页</a>");
        sb.append(c > 4 ? "<span class='pageEllipsis'>...</span>" : "");

        // 对冲参数
        long t = (c < 4) ? (4 - c) : (c > e - 4 ? e - c - 3 : 0);
        for (int i = -3; i <= 3; i++) {
            if (i + t == 0) {
                sb.append("<a class='current'>" + c + "</a>");
            } else {
                sb.append("<a class='num' to='?pageIndex=" + (c + i + t) + "'>" + (c + i + t) + "</a>");
            }
        }

        sb.append(c < (e - 3) ? "<span class='pageEllipsis'>...</span>" : "");
        sb.append("<a class='prev btn" + (c == e ? " disabled'" : "' to='?pageIndex=" + (c + 1) + "'") + ">下一页</a>");
        sb.append("<a class='end btn" + (c == e ? " disabled'" : "' to='?pageIndex=" + e + "'") + ">尾页</a>");
        sb.append("<label class='pageTurn'><input to='?pageIndex=%5BPAGE%5D' title='输入页码，按回车快速跳转' value='" + c + "'></input><span title='共 " + e + " 页'> / " + e + " 页</span></label>");
        sb.append("</div>");

        HashMap hashMap = new HashMap();
        hashMap.put("blogList", blogList);
        hashMap.put("pageInfo", sb.toString());
        return hashMap;
    }

    /**
     * 自定义SQL
     *
     * @param sql
     * @return
     */
    @Override
    public String sql(String sql) {
        return blogMapper.sql(sql).toString();
    }
}
