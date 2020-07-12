package cn.wgn.framework.web.service.impl;

import cn.wgn.framework.constant.Constants;
import cn.wgn.framework.exception.CommonException;
import cn.wgn.framework.utils.StringUtil;
import cn.wgn.framework.utils.job.ScheduleUtil;
import cn.wgn.framework.web.entity.JobEntity;
import cn.wgn.framework.web.mapper.JobMapper;
import cn.wgn.framework.web.service.IJobService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author WuGuangNuo
 * @date Created in 2020/6/14 14:04
 */
@Service
public class JobServiceImpl extends BaseServiceImpl<JobMapper, JobEntity> implements IJobService {
    @Autowired
    private JobMapper jobMapper;
    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void init() throws SchedulerException {
        scheduler.clear();
        List<JobEntity> jobList = jobMapper.selectList(new QueryWrapper<>());
        for (JobEntity job : jobList) {
            ScheduleUtil.createScheduleJob(scheduler, job);
        }
    }

    /**
     * 根据条件查询定时任务列表
     *
     * @param job
     * @return
     */
    @Override
    public List<JobEntity> selectJobPage(JobEntity job) {
        QueryWrapper<JobEntity> qw = new QueryWrapper<>();
        if (!StringUtil.isNullOrEmpty(job.getJobName())) {
            qw.lambda().like(JobEntity::getJobName, job.getJobName());
        }
        if (!StringUtil.isNullOrEmpty(job.getJobGroup())) {
            qw.lambda().eq(JobEntity::getJobGroup, job.getJobGroup());
        }
        if (!StringUtil.isNullOrEmpty(job.getStatus())) {
            qw.lambda().eq(JobEntity::getStatus, job.getStatus());
        }
        return jobMapper.selectList(qw);
    }

    /**
     * 定时任务立即执行一次
     *
     * @param jobId
     */
    @Override
    public Boolean run(Long jobId) throws SchedulerException {
        JobEntity jobEntity = this.getById(jobId);
        if (jobEntity == null) {
            throw new CommonException("jobId错误，未找到");
        }
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(Constants.TASK_PROPERTIES, jobEntity);
        scheduler.triggerJob(ScheduleUtil.getJobKey(jobId, jobEntity.getJobGroup()), dataMap);
        return Boolean.TRUE;
    }
}
