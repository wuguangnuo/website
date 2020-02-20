package cn.wgn.website.mapper;

import cn.wgn.website.entity.BlogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Blog Mapper 接口
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-02-16
 */
@Repository
public interface BlogMapper extends BaseMapper<BlogEntity> {

    /**
     * 获取博文列表
     *
     * @param page
     * @return
     */
//    IPage<BlogEntity> getBlogList(Page page);

    /**
     * 获取相关博客
     *
     * @param limit
     * @return
     */
    List<BlogEntity> getBlogSide(@Param("limit") Integer limit);
}
