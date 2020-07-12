package cn.wgn.website.mapper;

import cn.wgn.website.entity.BlogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

/**
 * <p>
 * 諾的博客 Mapper 接口
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-21
 */
@Repository
public interface BlogMapper extends BaseMapper<BlogEntity> {
    /**
     * 自定义SQL
     *
     * @param sql
     * @return
     */
    HashMap sql(@Param("sql") String sql);
}
