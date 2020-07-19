package cn.wgn.framework.web.service;

import cn.wgn.framework.web.entity.JobEntity;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * @author WuGuangNuo
 * @date Created in 2020/6/14 14:03
 */
public interface IJobService extends IBaseService<JobEntity> {
    /**
     * 创建调度任务
     *
     * @param job
     * @return
     */
    Boolean createJob(JobEntity job);

    /**
     * 更新调度任务
     *
     * @param job
     * @return
     */
    Boolean updateJob(JobEntity job);

    /**
     * 根据条件查询定时任务列表
     *
     * @param job
     * @return
     */
    List<JobEntity> selectJobPage(JobEntity job);

    /**
     * 定时任务立即执行一次
     *
     * @param jobId
     */
    Boolean run(Long jobId) throws SchedulerException;

    /**
     * 切换定时任务状态
     *
     * @param jobId
     * @return
     */
    Boolean changeStatus(Long jobId);
}
