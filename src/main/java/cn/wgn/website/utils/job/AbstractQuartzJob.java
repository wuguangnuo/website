package cn.wgn.website.utils.job;

import cn.wgn.website.entity.JobEntity;
import cn.wgn.website.entity.JobLogEntity;
import cn.wgn.website.service.impl.JobServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * 抽象quartz调用
 *
 * @author ruoyi
 */
@Slf4j
public abstract class AbstractQuartzJob implements Job {
//    @Autowired
//    private JobServiceImpl jobService;

    /**
     * 线程本地变量
     */
    private static ThreadLocal<LocalDateTime> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobEntity jobEntity = new JobEntity();
        BeanUtils.copyProperties(context.getMergedJobDataMap().get("TASK_PROPERTIES"), jobEntity);
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
     * 执行后
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
        jobLogEntity.setStartTm(startTime);
        jobLogEntity.setStopTm(LocalDateTime.now());
//        long runMs = Duration.between(jobLogEntity.getStartTm(), jobLogEntity.getStopTm()).toMillis();
//        jobLogEntity.setJobMessage(jobLogEntity.getJobName() + " 总共耗时：" + runMs + "毫秒");
        // todo
        jobLogEntity.setJobMessage("123123");
        if (e != null) {
            // status: FAIL
            jobLogEntity.setStatus("1");
            String errorMsg = e.getMessage();
            jobLogEntity.setExceptionInfo(errorMsg.length() > 2_000 ? errorMsg.substring(0, 2_000) : errorMsg);
        } else {
            // status: SUCCESS
            jobLogEntity.setStatus("0");
        }

        // 写入数据库当中
        SpringUtils.getBean(JobServiceImpl.class).insertLogininfor(jobLogEntity);
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
