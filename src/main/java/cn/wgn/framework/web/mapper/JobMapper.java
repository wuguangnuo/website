package cn.wgn.framework.web.mapper;

import cn.wgn.framework.web.entity.JobEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 定时任务调度表 Mapper 接口
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-07
 */
@Repository
public interface JobMapper extends BaseMapper<JobEntity> {

}
