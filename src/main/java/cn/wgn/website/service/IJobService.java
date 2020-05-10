package cn.wgn.website.service;

import cn.wgn.website.entity.JobEntity;
import cn.wgn.website.entity.JobLogEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * @author WuGuangNuo
 * @date Created in 2020/5/10 16:00
 */
public interface IJobService extends IService<JobEntity>, IBaseService {
    /**
     * 获取quartz调度器的计划任务
     *
     * @param jobEntity 调度信息
     * @return 调度任务集合
     */
    public List<JobEntity> selectJobList(JobEntity jobEntity);

    /**
     * 通过调度任务ID查询调度信息
     *
     * @param jobEntityId 调度任务ID
     * @return 调度任务对象信息
     */
    public JobEntity selectJobById(Long jobEntityId);

    /**
     * 暂停任务
     *
     * @param jobEntity 调度信息
     * @return 结果
     */
    public int pauseJob(JobEntity jobEntity) throws SchedulerException;

    /**
     * 恢复任务
     *
     * @param jobEntity 调度信息
     * @return 结果
     */
    public int resumeJob(JobEntity jobEntity) throws SchedulerException;

    /**
     * 删除任务后，所对应的trigger也将被删除
     *
     * @param jobEntity 调度信息
     * @return 结果
     */
    public int deleteJob(JobEntity jobEntity) throws SchedulerException;

    /**
     * 批量删除调度信息
     *
     * @param jobEntityIds 需要删除的任务ID
     * @return 结果
     */
    public void deleteJobByIds(Long[] jobEntityIds) throws SchedulerException;

    /**
     * 任务调度状态修改
     *
     * @param jobEntity 调度信息
     * @return 结果
     */
    public int changeStatus(JobEntity jobEntity) throws SchedulerException;

    /**
     * 立即运行任务
     *
     * @param jobEntity 调度信息
     * @return 结果
     */
    public void run(JobEntity jobEntity) throws SchedulerException;

    /**
     * 新增任务
     *
     * @param jobEntity 调度信息
     * @return 结果
     */
    public int insertJob(JobEntity jobEntity) throws SchedulerException;

    /**
     * 更新任务
     *
     * @param jobEntity 调度信息
     * @return 结果
     */
    public int updateJob(JobEntity jobEntity) throws SchedulerException;

    /**
     * 校验cron表达式是否有效
     *
     * @param cronExpression 表达式
     * @return 结果
     */
    public boolean checkCronExpressionIsValid(String cronExpression);

    // ================

    /**
     * 新增系统登录日志
     *
     * @param jobLogEntity 访问日志对象
     */
    public void insertLogininfor(JobLogEntity jobLogEntity);

    /**
     * 查询系统登录日志集合
     *
     * @param jobLogEntity 访问日志对象
     * @return 登录记录集合
     */
    public List<JobLogEntity> selectLogininforList(JobLogEntity jobLogEntity);

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return
     */
    public int deleteLogininforByIds(Long[] infoIds);

    /**
     * 清空系统登录日志
     */
    public void cleanLogininfor();
}
