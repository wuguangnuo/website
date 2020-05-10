package cn.wgn.website.service.impl;

import cn.wgn.website.entity.JobEntity;
import cn.wgn.website.entity.JobLogEntity;
import cn.wgn.website.mapper.JobLogMapper;
import cn.wgn.website.mapper.JobMapper;
import cn.wgn.website.service.IJobService;
import cn.wgn.website.utils.job.CronUtils;
import cn.wgn.website.utils.job.ScheduleUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author WuGuangNuo
 * @date Created in 2020/5/10 16:00
 */
@Service("jobService")
public class JobServiceImpl extends ServiceImpl<JobMapper, JobEntity> implements IJobService {
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private JobMapper jobMapper;
    @Autowired
    private JobLogMapper jobLogMapper;

    /**
     * 项目启动时，初始化定时器 主要是防止手动修改数据库导致未同步到定时任务处理（注：不能手动修改数据库ID和任务组名，否则会导致脏数据）
     */
    @PostConstruct
    public void init() throws SchedulerException {
        scheduler.clear();
        List<JobEntity> jobList = jobMapper.selectList(new QueryWrapper<>());
        for (JobEntity job : jobList) {
            ScheduleUtils.createScheduleJob(scheduler, job);
        }
    }

    /**
     * 获取quartz调度器的计划任务列表
     *
     * @param job 调度信息
     * @return
     */
    @Override
    public List<JobEntity> selectJobList(JobEntity job) {
//        ob_name like concat('%', #{jobName}, '%')
//			</if>
//			<if test="jobGroup != null and jobGroup != ''">
//                AND job_group = #{jobGroup}
//			</if>
//			<if test="status != null and status != ''">
//                AND status = #{status}
//			</if>
//			<if test="invokeTarget != null and invokeTarget != ''">
//                AND invoke_target
        return jobMapper.selectList(new QueryWrapper<>());
    }

    /**
     * 通过调度任务ID查询调度信息
     *
     * @param jobId 调度任务ID
     * @return 调度任务对象信息
     */
    @Override
    public JobEntity selectJobById(Long jobId) {
        return jobMapper.selectById(jobId);
    }

    /**
     * 暂停任务
     *
     * @param job 调度信息
     */
    @Override
    @Transactional
    public int pauseJob(JobEntity job) throws SchedulerException {
        Long jobId = job.getId();
        String jobGroup = job.getJobGroup();
        // 暂停 PAUSE 1
        job.setStatus("1");
        int rows = jobMapper.updateById(job);
        if (rows > 0) {
            scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }

    /**
     * 恢复任务
     *
     * @param job 调度信息
     */
    @Override
    @Transactional
    public int resumeJob(JobEntity job) throws SchedulerException {
        Long jobId = job.getId();
        String jobGroup = job.getJobGroup();
        // 正常 NORMAL 0
        job.setStatus("0");
        int rows = jobMapper.updateById(job);
        if (rows > 0) {
            scheduler.resumeJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }

    /**
     * 删除任务后，所对应的trigger也将被删除
     *
     * @param job 调度信息
     */
    @Override
    @Transactional
    public int deleteJob(JobEntity job) throws SchedulerException {
        Long jobId = job.getId();
        String jobGroup = job.getJobGroup();
        int rows = jobMapper.deleteById(jobId);
        if (rows > 0) {
            scheduler.deleteJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }

    /**
     * 批量删除调度信息
     *
     * @param jobIds 需要删除的任务ID
     * @return 结果
     */
    @Override
    @Transactional
    public void deleteJobByIds(Long[] jobIds) throws SchedulerException {
        for (Long jobId : jobIds) {
            JobEntity job = jobMapper.selectById(jobId);
            deleteJob(job);
        }
    }

    /**
     * 任务调度状态修改
     *
     * @param job 调度信息
     */
    @Override
    @Transactional
    public int changeStatus(JobEntity job) throws SchedulerException {
        int rows = 0;
        String status = job.getStatus();
        if ("0".equals(status)) {
            rows = resumeJob(job);
        } else if ("1".equals(status)) {
            rows = pauseJob(job);
        }
        return rows;
    }

    /**
     * 立即运行任务
     *
     * @param job 调度信息
     */
    @Override
    @Transactional
    public void run(JobEntity job) throws SchedulerException {
        Long jobId = job.getId();
        String jobGroup = job.getJobGroup();
        JobEntity properties = selectJobById(job.getId());
        // 参数
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("TASK_PROPERTIES", properties);
        scheduler.triggerJob(ScheduleUtils.getJobKey(jobId, jobGroup), dataMap);
    }

    /**
     * 新增任务
     *
     * @param job 调度信息 调度信息
     */
    @Override
    @Transactional
    public int insertJob(JobEntity job) throws SchedulerException {
        job.setStatus("1");
        int rows = jobMapper.insert(job);
        if (rows > 0) {
            ScheduleUtils.createScheduleJob(scheduler, job);
        }
        return rows;
    }

    /**
     * 更新任务的时间表达式
     *
     * @param job 调度信息
     */
    @Override
    @Transactional
    public int updateJob(JobEntity job) throws SchedulerException {
        JobEntity properties = selectJobById(job.getId());
        int rows = jobMapper.updateById(job);
        if (rows > 0) {
            updateSchedulerJob(job, properties.getJobGroup());
        }
        return rows;
    }

    /**
     * 更新任务
     *
     * @param job      任务对象
     * @param jobGroup 任务组名
     */
    public void updateSchedulerJob(JobEntity job, String jobGroup) throws SchedulerException {
        Long jobId = job.getId();
        // 判断是否存在
        JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(jobKey);
        }
        ScheduleUtils.createScheduleJob(scheduler, job);
    }

    /**
     * 校验cron表达式是否有效
     *
     * @param cronExpression 表达式
     * @return 结果
     */
    @Override
    public boolean checkCronExpressionIsValid(String cronExpression) {
        return CronUtils.isValid(cronExpression);
    }

    /**
     * 新增系统登录日志
     *
     * @param jobLogEntity 访问日志对象
     */
    @Override
    public void insertLogininfor(JobLogEntity jobLogEntity) {
        jobLogMapper.insert(jobLogEntity);
    }

    /**
     * 查询系统登录日志集合
     *
     * @param jobLogEntity 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<JobLogEntity> selectLogininforList(JobLogEntity jobLogEntity) {
//        <if test="ipaddr != null and ipaddr != ''">
//                AND ipaddr like concat('%', #{ipaddr}, '%')
//			</if>
//			<if test="status != null and status != ''">
//                AND status = #{status}
//			</if>
//			<if test="userName != null and userName != ''">
//                AND user_name like concat('%', #{userName}, '%')
//			</if>
//			<if test="beginTime != null and beginTime != ''"><!-- 开始时间检索 -->
//                and date_format(login_time,'%y%m%d') &gt;= date_format(#{beginTime},'%y%m%d')
//			</if>
//			<if test="endTime != null and endTime != ''"><!-- 结束时间检索 -->
//                and date_format(login_time,'%y%m%d') &lt;= date_format(#{endTime},'%y%m%d')
//			</if>
        return jobLogMapper.selectList(new QueryWrapper<>());
    }

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return
     */
    @Override
    public int deleteLogininforByIds(Long[] infoIds) {
//        return jobLogMapper.deleteLogininforByIds(infoIds);
        return 0;
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLogininfor() {
        // truncate table sys_logininfor
//        logininforMapper.cleanLogininfor();
    }
}
