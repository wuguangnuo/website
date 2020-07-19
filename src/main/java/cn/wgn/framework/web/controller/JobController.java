package cn.wgn.framework.web.controller;

import cn.wgn.framework.aspectj.annotation.Authorize;
import cn.wgn.framework.exception.CommonException;
import cn.wgn.framework.utils.excel.ExcelUtil;
import cn.wgn.framework.web.ApiRes;
import cn.wgn.framework.web.entity.JobEntity;
import cn.wgn.framework.web.entity.JobLogEntity;
import cn.wgn.framework.web.service.IJobLogService;
import cn.wgn.framework.web.service.IJobService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 调度任务
 *
 * @author WuGuangNuo
 * @date Created in 2020/6/14 13:57
 */
@Api(tags = "*调度")
@RestController
@RequestMapping("job")
public class JobController extends BaseController<JobEntity> {
    @Autowired
    private IJobService jobService;
    @Autowired
    private IJobLogService jobLogService;

    /**
     * 根据主键查询单条数据
     *
     * @param id id
     * @return Entity
     */
    @Override
    @Authorize
    protected JobEntity getById(Long id) {
        return jobService.getById(id);
    }

    /**
     * 删除数据
     *
     * @return Integer
     */
    @Authorize
    @Override
    protected Boolean delete(Long id) {
        return jobService.removeById(id);
    }

    /**
     * 查询数据条数
     *
     * @return Integer
     */
    @Authorize
    @Override
    protected Integer count() {
        return jobService.count();
    }

    /**
     * 创建调度任务
     *
     * @return Integer
     */
    @Authorize
    @PostMapping("createJob")
    protected ApiRes<String> createJob(@RequestBody JobEntity jobEntity) {
        Boolean res = jobService.createJob(jobEntity);
        if (res) {
            return ApiRes.suc("创建任务成功");
        } else {
            return ApiRes.fail("创建任务失败");
        }
    }

    /**
     * 更新调度任务
     *
     * @return Integer
     */
    @Authorize
    @PostMapping("updateJob")
    protected ApiRes<String> updateJob(@RequestBody JobEntity jobEntity) {
        Boolean res = jobService.updateJob(jobEntity);
        if (res) {
            return ApiRes.suc("更新任务成功");
        } else {
            return ApiRes.fail("更新任务失败");
        }
    }

    /**
     * 查询定时任务列表
     */
    @Authorize
    @GetMapping("/list")
    public ApiRes<PageInfo<JobEntity>> list(JobEntity jobEntity) {
        startPage();
        List<JobEntity> data = jobService.selectJobPage(jobEntity);
        return pageData(data);
    }

    /**
     * 导出定时任务列表
     */
    @Authorize
    @GetMapping("/export")
    public void export(HttpServletResponse response, JobEntity jobEntity) {
        List<JobEntity> data = jobService.selectJobPage(jobEntity);

        // todo,改造Excel导出工具
        String fileName = "渠道统计";
        String excelName = "Sheet1";
        String excelTitles = "渠道码,渠道名称";
        try {
            ExcelUtil.exportExcel(response, fileName, excelName, excelTitles, data);
        } catch (IOException e) {
            throw new CommonException("生成表格失败，IO异常", e);
        } catch (IllegalAccessException e) {
            throw new CommonException("生成表格异常，获取数据异常", e);
        }
    }

    /**
     * 定时任务立即执行一次
     */
    @Authorize
    @GetMapping("run/{id}")
    public ApiRes<String> run(@PathVariable("id") Long id) throws SchedulerException {
        Boolean isSuc = jobService.run(id);
        if (isSuc) {
            return ApiRes.suc("执行成功");
        } else {
            return ApiRes.err("执行失败");
        }
    }

    /**
     * 切换定时任务状态
     */
    @Authorize
    @GetMapping("changeStatus/{id}")
    public ApiRes<String> changeStatus(@PathVariable("id") Long id) {
        Boolean isSuc = jobService.changeStatus(id);
        if (isSuc) {
            return ApiRes.suc("执行成功");
        } else {
            return ApiRes.err("执行失败");
        }
    }

    // ======== JOB_LOG ========

    /**
     * 查询定时任务日志列表
     */
    @Authorize
    @GetMapping("/log/list")
    public ApiRes<PageInfo<JobLogEntity>> loglist(JobLogEntity jobLogEntity) {
        startPage();
        List<JobLogEntity> data = jobLogService.selectJobLogPage(jobLogEntity);
        if (data == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(new PageInfo<>(data));
        }
    }

    /**
     * 导出定时任务调度日志列表
     */
    @Authorize
    @GetMapping("/log/export")
    public ApiRes<PageInfo<JobLogEntity>> logExport(JobLogEntity jobLogEntity) {
        return ApiRes.fail("待开发," + jobLogEntity.toString());
    }

    /**
     * 根据调度编号获取详细信息
     */
    @Authorize
    @GetMapping("log/{id}")
    public ApiRes<JobLogEntity> getLog(@PathVariable Long id) {
        JobLogEntity data = jobLogService.getById(id);
        if (data == null) {
            return ApiRes.fail("查询为空");
        } else {
            return ApiRes.suc(data);
        }
    }


    /**
     * 删除定时任务调度日志(多条)
     */
    @Authorize
    @DeleteMapping("log/{ids}")
    public ApiRes<String> logRemove(@PathVariable Long[] ids) {
        int sucNum = jobLogService.logRemove(ids);
        return ApiRes.suc("成功删除[" + sucNum + "]条数据");
    }

    /**
     * 清空定时任务日志
     */
    @Authorize
    @DeleteMapping("log/clean")
    public ApiRes<String> logClean() {
        jobLogService.truncate();
        return ApiRes.suc();
    }
}
