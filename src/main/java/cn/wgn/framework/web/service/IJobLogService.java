package cn.wgn.framework.web.service;

import cn.wgn.framework.web.entity.JobLogEntity;

import java.util.List;

/**
 * @author WuGuangNuo
 * @date Created in 2020/6/14 14:04
 */
public interface IJobLogService extends IBaseService<JobLogEntity> {
    /**
     * 根据条件查询定时任务列表
     *
     * @param jobLog
     * @return
     */
    List<JobLogEntity> selectJobLogPage(JobLogEntity jobLog);

    /**
     * 删除定时任务调度日志(多条)
     *
     * @param ids ids
     * @return sucNum
     */
    int logRemove(Long[] ids);

    /**
     * 清空定时任务日志
     */
    void truncate();
}
