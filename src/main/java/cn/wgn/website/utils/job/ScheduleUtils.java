package cn.wgn.website.utils.job;

import cn.wgn.website.entity.JobEntity;
import org.quartz.*;

/**
 * 定时任务工具类
 *
 * @author ruoyi
 */
public class ScheduleUtils {
    /**
     * 得到quartz任务类
     *
     * @param jobEntity 执行计划
     * @return 具体执行任务类
     */
    private static Class<? extends Job> getQuartzJobClass(JobEntity jobEntity) {
        boolean isConcurrent = "0".equals(jobEntity.getConcurrent());
        return isConcurrent ? QuartzJobExecution.class : QuartzDisallowConcurrentExecution.class;
    }

    /**
     * 构建任务触发对象
     */
    public static TriggerKey getTriggerKey(Long jobId, String jobGroup) {
        return TriggerKey.triggerKey("TASK_CLASS_NAME" + jobId, jobGroup);
    }

    /**
     * 构建任务键对象
     */
    public static JobKey getJobKey(Long jobId, String jobGroup) {
        return JobKey.jobKey("TASK_CLASS_NAME" + jobId, jobGroup);
    }

    /**
     * 创建定时任务
     */
    public static void createScheduleJob(Scheduler scheduler, JobEntity jobEntity) throws SchedulerException {
        Class<? extends Job> jobClass = getQuartzJobClass(jobEntity);
        // 构建job信息
        Long jobId = jobEntity.getId();
        String jobGroup = jobEntity.getJobGroup();
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(getJobKey(jobId, jobGroup)).build();

        // 表达式调度构建器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(jobEntity.getCronExpression());
        cronScheduleBuilder = handleCronScheduleMisfirePolicy(jobEntity, cronScheduleBuilder);

        // 按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(jobId, jobGroup))
                .withSchedule(cronScheduleBuilder).build();

        // 放入参数，运行时的方法可以获取
        jobDetail.getJobDataMap().put("TASK_PROPERTIES", jobEntity);

        // 判断是否存在
        if (scheduler.checkExists(getJobKey(jobId, jobGroup))) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(getJobKey(jobId, jobGroup));
        }

        scheduler.scheduleJob(jobDetail, trigger);

        // 暂停任务 PAUSE: 1
        if (jobEntity.getStatus().equals("1")) {
            scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
    }

    /**
     * 设置定时任务策略
     */
    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(JobEntity jobEntity, CronScheduleBuilder cb)
            throws RuntimeException {
//        默认 MISFIRE_DEFAULT = "0";
//        立即触发执行 MISFIRE_IGNORE_MISFIRES = "1";
//        触发一次执行 MISFIRE_FIRE_AND_PROCEED = "2";
//        不触发立即执行 MISFIRE_DO_NOTHING = "3";
        switch (jobEntity.getMisfirePolicy()) {
            case "0":
                return cb;
            case "1":
                return cb.withMisfireHandlingInstructionIgnoreMisfires();
            case "2":
                return cb.withMisfireHandlingInstructionFireAndProceed();
            case "3":
                return cb.withMisfireHandlingInstructionDoNothing();
            default:
                throw new RuntimeException("The task misfire policy '" + jobEntity.getMisfirePolicy()
                        + "' cannot be used in cron schedule tasks");
        }
    }
}