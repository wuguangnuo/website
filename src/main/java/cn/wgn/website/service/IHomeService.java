package cn.wgn.website.service;

import cn.wgn.website.dto.home.BlogListQuery;
import cn.wgn.website.dto.home.*;
import cn.wgn.website.entity.*;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/16 10:50
 */
public interface IHomeService {
    /**
     * 获取最新一篇日记
     *
     * @return
     */
    DiaryDto getLastDiary();

    /**
     * 获取实例列表
     *
     * @return
     */
    IPage<DemoEntity> getDemo();

    /**
     * 游戏列表
     *
     * @return
     */
    IPage<GameEntity> getGame();

    /**
     * 开发文档列表
     *
     * @return
     */
    IPage<DocEntity> getDoc();

    /**
     * 工具箱列表
     *
     * @return
     */
    IPage<ToolEntity> getTool();

    /**
     * 获取博文列表
     *
     * @param query 查询条件
     * @return
     */
    IPage<BlogEntity> getBlogList(BlogListQuery query);

    /**
     * 获取相关博客
     *
     * @return
     */
    IPage<BlogEntity> getBlogSide();

    /**
     * 获取博文详情
     *
     * @param id 博文ID
     * @return
     */
    BlogEntity getBlogDetail(Integer id);
}
