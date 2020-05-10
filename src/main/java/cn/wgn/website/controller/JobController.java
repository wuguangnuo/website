package cn.wgn.website.controller;

import cn.wgn.website.dto.ApiRes;
import cn.wgn.website.entity.JobEntity;
import cn.wgn.website.entity.JobLogEntity;
import cn.wgn.website.service.IJobService;
import io.swagger.annotations.Api;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文件分享系统
 *
 * @author WuGuangNuo
 * @date Created in 2020/3/12 22:06
 */
@RequestMapping("job")
@Api(tags = "调度任务")
@RestController
public class JobController extends BaseController {
    @Autowired
    private IJobService jobService;

    /**
     * 查询定时任务列表
     */
    @GetMapping("/list")
    public ApiRes<List> list(JobEntity jobEntity) {
        List<JobEntity> list = jobService.selectJobList(jobEntity);
        return ApiRes.suc("s", list);
    }

    /**
     * 导出定时任务列表
     */
//    @Log(title = "定时任务", businessType = BusinessType.EXPORT)
//    @GetMapping("/export")
//    public AjaxResult export(JobEntity jobEntity) {
//        List<JobEntity> list = jobService.selectJobList(jobEntity);
//        ExcelUtil<JobEntity> util = new ExcelUtil<JobEntity>(JobEntity.class);
//        return util.exportExcel(list, "定时任务");
//    }

    /**
     * 获取定时任务详细信息
     */
    @GetMapping(value = "/{jobId}")
    public ApiRes getInfo(@PathVariable("jobId") Long jobId) {
        return ApiRes.suc(jobService.selectJobById(jobId));
    }

    /**
     * 新增定时任务
     */
    @PostMapping
    public ApiRes add(@RequestBody JobEntity jobEntity) throws SchedulerException {
        return ApiRes.suc(jobService.insertJob(jobEntity));
    }

    /**
     * 修改定时任务
     */
    @PutMapping
    public ApiRes edit(@RequestBody JobEntity jobEntity) throws SchedulerException {
        return ApiRes.suc(jobService.updateJob(jobEntity));
    }

    /**
     * 定时任务状态修改
     */
    @PutMapping("/changeStatus")
    public ApiRes changeStatus(@RequestBody JobEntity job) throws SchedulerException {
        JobEntity newJob = jobService.selectJobById(job.getId());
        newJob.setStatus(job.getStatus());
        return ApiRes.suc(jobService.changeStatus(newJob));
    }

    /**
     * 定时任务立即执行一次
     */
    @PutMapping("/run")
    public ApiRes run(@RequestBody JobEntity job) throws SchedulerException {
        jobService.run(job);
        return ApiRes.suc();
    }

    /**
     * 删除定时任务
     */
    @DeleteMapping("/{jobIds}")
    public ApiRes removeJob(@PathVariable Long[] jobIds) throws SchedulerException {
        jobService.deleteJobByIds(jobIds);
        return ApiRes.suc();
    }

    // ================

    @GetMapping("/log/list")
    public ApiRes list(JobLogEntity jobLogEntity) {
        List<JobLogEntity> list = jobService.selectLogininforList(jobLogEntity);
        return ApiRes.suc("s", list);
    }

//    @GetMapping("/export")
//    public ApiRes export(SysLogininfor logininfor) {
//        List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
//        ExcelUtil<SysLogininfor> util = new ExcelUtil<SysLogininfor>(SysLogininfor.class);
//        return util.exportExcel(list, "登陆日志");
//    }

    @DeleteMapping("/log/{infoIds}")
    public ApiRes removeJobLog(@PathVariable Long[] infoIds) {
        return ApiRes.suc(jobService.deleteLogininforByIds(infoIds));
    }

    @DeleteMapping("/log/clean")
    public ApiRes clean() {
        jobService.cleanLogininfor();
        return ApiRes.suc();
    }
}
