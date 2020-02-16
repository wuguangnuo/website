package cn.wgn.website.mapper;

import cn.wgn.website.entity.ToolEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Tool Mapper 接口
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-02-16
 */
@Repository
public interface ToolMapper extends BaseMapper<ToolEntity> {
    /**
     * 获取工具箱列表
     *
     * @param page
     * @return
     */
    IPage<ToolEntity> getTool(Page page);
}
