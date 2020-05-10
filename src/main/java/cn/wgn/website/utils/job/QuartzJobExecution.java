package cn.wgn.website.utils.job;

import cn.wgn.website.entity.JobEntity;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（允许并发执行）
 *
 * @author ruoyi
 */
public class QuartzJobExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, JobEntity jobEntity) throws Exception {
        JobInvokeUtil.invokeMethod(jobEntity);
    }
}
