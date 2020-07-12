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
     * 查
     *
     * @param id id
     * @return Entity
     */
    @Override
    @Authorize
    public JobEntity getById(Long id) {
        return jobService.getById(id);
    }

    /**
     * 数量
     *
     * @return Integer
     */
    @Authorize
    @Override
    public Integer count() {
        return jobService.count();
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
     * 查询定时任务日志列表
     */
    @Authorize
    @GetMapping("/loglist")
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

}
