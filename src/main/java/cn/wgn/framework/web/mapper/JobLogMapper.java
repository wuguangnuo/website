package cn.wgn.framework.web.mapper;

import cn.wgn.framework.web.entity.JobLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 定时任务调度日志表 Mapper 接口
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-07
 */
@Repository
public interface JobLogMapper extends BaseMapper<JobLogEntity> {

    /**
     * 清空定时任务日志
     */
    void truncate();
}
