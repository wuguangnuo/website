package cn.wgn.framework.utils.job;

import cn.wgn.framework.utils.StringUtil;
import cn.wgn.framework.utils.spring.SpringUtil;
import cn.wgn.framework.web.entity.JobEntity;
import cn.wgn.framework.web.entity.JobLogEntity;
import cn.wgn.framework.web.service.IJobLogService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 抽象quartz调用
 *
 * @author WuGuangNuo
 * @date Created in 2020/6/14 21:37
 */
public abstract class AbstractQuartzJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(AbstractQuartzJob.class);

    /**
     * 线程本地变量
     */
    private static ThreadLocal<LocalDateTime> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) {
        JobEntity jobEntity = new JobEntity();
        BeanUtils.copyProperties(context.getMergedJobDataMap().get(JobConstants.TASK_PROPERTIES), jobEntity);
        try {
            before(context, jobEntity);
            if (jobEntity != null) {
                doExecute(context, jobEntity);
            }
            after(context, jobEntity, null);
        } catch (Exception e) {
            log.error("任务执行异常  - ：", e);
            after(context, jobEntity, e);
        }
    }

    /**
     * 执行前
     *
     * @param context   工作执行上下文对象
     * @param jobEntity 系统计划任务
     */
    protected void before(JobExecutionContext context, JobEntity jobEntity) {
        threadLocal.set(LocalDateTime.now());
    }

    /**
     * 执行后将执行日志写入 JOB_LOG
     *
     * @param context   工作执行上下文对象
     * @param jobEntity 系统计划任务
     */
    protected void after(JobExecutionContext context, JobEntity jobEntity, Exception e) {
        LocalDateTime startTime = threadLocal.get();
        threadLocal.remove();

        final JobLogEntity jobLogEntity = new JobLogEntity();
        jobLogEntity.setJobName(jobEntity.getJobName());
        jobLogEntity.setJobGroup(jobEntity.getJobGroup());
        jobLogEntity.setInvokeTarget(jobEntity.getInvokeTarget());
        jobLogEntity.setStartTime(startTime);
        jobLogEntity.setStopTime(LocalDateTime.now());
        // todo 使用DateUtil处理
        long runMs = jobLogEntity.getStopTime().toInstant(ZoneOffset.of("+8")).toEpochMilli()
                - jobLogEntity.getStartTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        jobLogEntity.setJobMessage(jobLogEntity.getJobName() + " 总共耗时：" + runMs + "毫秒");
        if (e != null) {
            jobLogEntity.setStatus(JobConstants.FAIL);
            String errorMsg = StringUtil.substring(((InvocationTargetException) e).getTargetException().toString(), 0, 500);
            Throwable cause = ((InvocationTargetException) e).getTargetException().getCause();
            if (cause != null) {
                errorMsg = errorMsg + "\r\n" + StringUtil.substring(cause.toString(), 0, 1500);
            }
            jobLogEntity.setExceptionInfo(errorMsg);
        } else {
            jobLogEntity.setStatus(JobConstants.SUCESS);
        }

        // 写入数据库当中
        SpringUtil.getBean(IJobLogService.class).save(jobLogEntity);
    }

    /**
     * 执行方法，由子类重载
     *
     * @param context   工作执行上下文对象
     * @param jobEntity 系统计划任务
     * @throws Exception 执行过程中的异常
     */
    protected abstract void doExecute(JobExecutionContext context, JobEntity jobEntity) throws Exception;
}
