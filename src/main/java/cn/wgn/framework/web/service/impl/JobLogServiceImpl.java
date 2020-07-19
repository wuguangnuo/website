package cn.wgn.framework.web.service.impl;

import cn.wgn.framework.web.entity.JobLogEntity;
import cn.wgn.framework.web.mapper.JobLogMapper;
import cn.wgn.framework.web.service.IJobLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author WuGuangNuo
 * @date Created in 2020/6/14 14:06
 */
@Service
public class JobLogServiceImpl extends BaseServiceImpl<JobLogMapper, JobLogEntity> implements IJobLogService {
    @Autowired
    private JobLogMapper jobLogMapper;

    /**
     * 根据条件查询定时任务列表
     *
     * @param jobLog
     * @return
     */
    @Override
    public List<JobLogEntity> selectJobLogPage(JobLogEntity jobLog) {
        return jobLogMapper.selectList(new QueryWrapper<>());
    }

    /**
     * 删除定时任务调度日志(多条)
     *
     * @param ids ids
     * @return sucNum
     */
    @Override
    public int logRemove(Long[] ids) {
        if (ids.length == 0) {
            return 0;
        }

        List<Long> idList = Arrays.asList(ids);
        return jobLogMapper.deleteBatchIds(idList);
    }

    /**
     * 清空定时任务日志
     */
    @Override
    public void truncate() {
        jobLogMapper.truncate();
    }
}
