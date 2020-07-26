package cn.wgn.framework.web.service.impl;

import cn.wgn.framework.exception.CommonException;
import cn.wgn.framework.utils.StringUtil;
import cn.wgn.framework.utils.job.JobConstants;
import cn.wgn.framework.utils.job.ScheduleUtil;
import cn.wgn.framework.web.entity.JobEntity;
import cn.wgn.framework.web.mapper.JobMapper;
import cn.wgn.framework.web.service.IJobService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Qualifier("schedulerFactoryBean")
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
     * 创建调度任务
     *
     * @param job
     * @return
     */
    @Override
    @Transactional
    public Boolean createJob(JobEntity job) {
        job.setStatus(JobConstants.Status.PAUSE.getValue());
        if (this.save(job)) {
            try {
                ScheduleUtil.createScheduleJob(scheduler, job);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 更新调度任务
     *
     * @param job
     * @return
     */
    @Override
    public Boolean updateJob(JobEntity job) {
        if (StringUtil.isNull(job.getId())) {
            return Boolean.FALSE;
        }
        JobEntity properties = getById(job.getId());
        try {
            updateSchedulerJob(job, properties.getJobGroup());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return this.updateById(job);
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
        dataMap.put(JobConstants.TASK_PROPERTIES, jobEntity);
        scheduler.triggerJob(ScheduleUtil.getJobKey(jobId, jobEntity.getJobGroup()), dataMap);
        return Boolean.TRUE;
    }

    /**
     * 切换定时任务状态
     *
     * @param jobId
     * @return
     */
    @Override
    public Boolean changeStatus(Long jobId) {
        JobEntity entity = this.getById(jobId);
        if (entity == null) {
            return Boolean.FALSE;
        }

        try {
            if (JobConstants.Status.NORMAL.getValue().equals(entity.getStatus())) {
                this.pauseJob(entity);
            } else if (JobConstants.Status.PAUSE.getValue().equals(entity.getStatus())) {
                this.resumeJob(entity);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return Boolean.TRUE;
    }

    /**
     * 暂停任务
     *
     * @param job 调度信息
     */
    @Transactional
    public void pauseJob(JobEntity job) throws SchedulerException {
        Long jobId = job.getId();
        String jobGroup = job.getJobGroup();
        job.setStatus(JobConstants.Status.PAUSE.getValue());
        if (updateById(job)) {
            scheduler.pauseJob(ScheduleUtil.getJobKey(jobId, jobGroup));
        } else {
            throw new CommonException("暂停任务方法中，更新任务失败");
        }
    }

    /**
     * 恢复任务
     *
     * @param job 调度信息
     */
    @Transactional
    public void resumeJob(JobEntity job) throws SchedulerException {
        Long jobId = job.getId();
        String jobGroup = job.getJobGroup();
        job.setStatus(JobConstants.Status.NORMAL.getValue());
        if (updateById(job)) {
            scheduler.resumeJob(ScheduleUtil.getJobKey(jobId, jobGroup));
        } else {
            throw new CommonException("恢复任务方法中，更新任务失败");
        }
    }

    /**
     * 更新任务
     *
     * @param job      任务对象
     * @param jobGroup 任务组名
     */
    private void updateSchedulerJob(JobEntity job, String jobGroup) throws SchedulerException {
        Long jobId = job.getId();
        // 判断是否存在
        JobKey jobKey = ScheduleUtil.getJobKey(jobId, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(jobKey);
        }
        ScheduleUtil.createScheduleJob(scheduler, job);
    }
}
