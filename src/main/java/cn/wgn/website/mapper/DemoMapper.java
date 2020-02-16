package cn.wgn.website.mapper;

import cn.wgn.website.entity.DemoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Demo Mapper 接口
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-02-16
 */
@Repository
public interface DemoMapper extends BaseMapper<DemoEntity> {
    /**
     * 获取示例列表
     *
     * @param page
     * @return
     */
    IPage<DemoEntity> getDemo(Page page);
}
