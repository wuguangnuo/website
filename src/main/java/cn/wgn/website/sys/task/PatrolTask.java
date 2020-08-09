package cn.wgn.website.sys.task;

import cn.wgn.framework.utils.DateUtil;
import cn.wgn.framework.utils.HtmlModel;
import cn.wgn.framework.utils.job.JobConstants;
import cn.wgn.framework.utils.mail.EmailInfo;
import cn.wgn.framework.utils.mail.EmailUtil;
import cn.wgn.framework.web.entity.JobLogEntity;
import cn.wgn.framework.web.service.IJobLogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.management.OperatingSystemMXBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * 巡警任务
 *
 * @author WuGuangNuo
 * @date Created in 2020/7/24 21:53
 */
@Slf4j
@Component("patrolTask")
public class PatrolTask {
    private static int count = 0;
    private static final long TM = System.currentTimeMillis();
    private static long lastEmail = 0;
    @Autowired
    private IJobLogService jobLogService;

    /**
     * 巡警医生
     */
    public void doctor() {
        count++;
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long totalPhysicalMemorySize = osmxb.getTotalPhysicalMemorySize();
        long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();
        Double memoryPercent = (1 - (double) freePhysicalMemorySize / (double) totalPhysicalMemorySize) * 100D;

        // 定时任务数量状态
        LambdaQueryWrapper<JobLogEntity> qw = new QueryWrapper<JobLogEntity>().lambda().between(
                JobLogEntity::getStopTime,
                DateUtil.toLocalDateTime(DateUtil.dateAddDays(null, -1)),
                LocalDateTime.now());
        int allNum = jobLogService.count(qw);
        qw.eq(JobLogEntity::getStatus, JobConstants.FAIL);
        int errNum = jobLogService.count(qw);

        // 定时任务错误警报邮件：
        // 最近10次出现大于等于5次异常发出警报
        // 警报每天最多发一次。（00:00-08:00不发送）
        List<JobLogEntity> list = jobLogService.list(
                new LambdaQueryWrapper<JobLogEntity>()
                        .select(JobLogEntity::getStatus)
                        .orderByDesc(JobLogEntity::getId)
                        .last("LIMIT 10")
        );
        long x = list.stream().map(JobLogEntity::getStatus).filter(JobConstants.FAIL::equals).count();

        String report = "<p>======== REPORT ========" +
                "</p><p>现在时间：" + DateUtil.dateFormat(null, DateUtil.HOUR_PATTERN) + "，系统已运行：" + DateUtil.diffDate(TM) +
                "</p><p>内存使用率：" + new DecimalFormat("###.###").format(memoryPercent) + "%" +
                "</p><p>巡警医生执行次数：" + count +
                "</p><p>定时任务最近一天内执行次数：" + allNum + "，其中错误次数：" + errNum +
                "</p><p>定时任务最近10次执行错误数：" + x +
                "</p><p>======== END ========</p>";

        if (x >= 5 && isEmail()) {
            lastEmail = System.currentTimeMillis();
            String subject = "巡警医生报告邮件 from wgn API";
            String content = HtmlModel.mailBody("巡警医生报告邮件", report);
            EmailInfo emailInfo = new EmailInfo("wuguangnuo@qq.com", subject, content);
            EmailUtil.sendHtmlMail(emailInfo);
        }
        log.info(report.replaceAll("<p>", "\r\n").replaceAll("</p>", ""));
    }

    /**
     * 当前时间段是否可发送邮件
     * 距上一次大于24小时，不在0:00-8:00时段
     */
    private static boolean isEmail() {
        long oneDay = 24 * 3600 * 1000L;
        if (System.currentTimeMillis() - lastEmail < oneDay) {
            return false;
        }
        LocalTime now = LocalTime.now();
        LocalTime start = LocalTime.of(0, 0);
        LocalTime end = LocalTime.of(8, 0);
        return !(now.isAfter(start) && now.isBefore(end));
    }
}
