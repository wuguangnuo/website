package cn.wgn.framework.utils.job;

import cn.wgn.framework.utils.DateUtil;
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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

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
        long runMs = DateUtil.diff(jobLogEntity.getStartTime(), jobLogEntity.getStopTime());
        jobLogEntity.setJobMessage(jobLogEntity.getJobName() + " 总共耗时：" + runMs + "毫秒");
        if (e != null) {
            jobLogEntity.setStatus(JobConstants.FAIL);

            // 获取exception的详细错误信息
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            String errorMsg = StringUtil.substring(sw.toString(), 0, 2000);
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
