package cn.wgn.framework.utils.job;

/**
 * 调度任务常量
 *
 * @author WuGuangNuo
 * @date Created in 2020/7/12 11:43
 */
public class JobConstants {
    // TASK 常量

    /**
     * 执行目标key
     */
    public static final String TASK_PROPERTIES = "TASK_PROPERTIES";
    /**
     * 执行目标name
     */
    public static final String TASK_CLASS_NAME = "TASK_CLASS_NAME";

    // TASK 任务状态

    /**
     * 任务状态：正常
     */
    public static final String NORMAL = "0";
    /**
     * 任务状态：暂停
     */
    public static final String PAUSE = "1";

    // TASK 任务执行状态(JobLog)

    /**
     * 任务执行状态：失败
     */
    public static final String FAIL = "1";
    /**
     * 任务执行状态：成功
     */
    public static final String SUCESS = "0";

    // TASK 任务并发策略

    /**
     * 允许并发
     */
    public static final String CONCURRENT = "0";
    /**
     * 不允许并发
     */
    public static final String NO_CONCURRENT = "1";

    // TASK 任务计划执行错误策略

    /**
     * 默认
     */
    public static final String MISFIRE_DEFAULT = "0";

    /**
     * 立即触发执行
     */
    public static final String MISFIRE_IGNORE_MISFIRES = "1";

    /**
     * 触发一次执行
     */
    public static final String MISFIRE_FIRE_AND_PROCEED = "2";

    /**
     * 不触发立即执行
     */
    public static final String MISFIRE_DO_NOTHING = "3";
}
