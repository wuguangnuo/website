package cn.wgn.framework.utils.job;

import cn.wgn.framework.web.entity.JobEntity;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（禁止并发执行）
 *
 * @author WuGuangNuo
 * @date Created in 2020/6/14 21:37
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, JobEntity jobEntity) throws Exception {
        JobInvokeUtil.invokeMethod(jobEntity);
    }
}
