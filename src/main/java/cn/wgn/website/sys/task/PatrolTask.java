package cn.wgn.website.sys.task;

import cn.wgn.framework.utils.DateUtil;
import cn.wgn.framework.utils.EncryptUtil;
import cn.wgn.framework.utils.HtmlModel;
import cn.wgn.framework.utils.HttpUtil;
import cn.wgn.framework.utils.job.JobConstants;
import cn.wgn.framework.utils.mail.EmailInfo;
import cn.wgn.framework.utils.mail.EmailUtil;
import cn.wgn.framework.web.entity.JobLogEntity;
import cn.wgn.framework.web.service.IJobLogService;
import cn.wgn.website.sys.mapper.TaskMapper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
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
    /**
     * 执行次数
     */
    private static int count = 0;
    /**
     * 系统启动时间戳
     */
    private static final long TM = System.currentTimeMillis();
    /**
     * 最近发送邮件时间戳
     */
    private static long lastEmail = 0;
    /**
     * 宝塔API接口密钥
     */
    @Value("${private-config.bt.btSign}")
    private String btSign;

    /**
     * 数据库库名
     */
    private static String dbName = "wuguangnuo";
    /**
     * 数据库容量信息(前5条)
     */
    private static int dbInfoLimit = 5;
    /**
     * 获取系统基础统计API
     */
    private static final String SYSTEM_TOTAL = "http://bt.wuguangnuo.cn:8888/system?action=GetSystemTotal";
    @Autowired
    private IJobLogService jobLogService;
    @Autowired
    private TaskMapper taskMapper;

    /**
     * 巡警医生
     */
    public void doctor() {
        count++;
        // 获取系统状态信息
        String sysJson = getSystemTotal();
        JSONObject object = JSON.parseObject(sysJson);
        boolean isSysSuc = Boolean.TRUE;
        if (object.get("status") != null && !(Boolean) object.get("status")) {
            // 获取系统信息失败
            isSysSuc = false;
        }

        // 定时任务数量状态
        LambdaQueryWrapper<JobLogEntity> qw = new QueryWrapper<JobLogEntity>().lambda().between(
                JobLogEntity::getStopTime,
                DateUtil.toLocalDateTime(DateUtil.dateAddDays(null, -1)),
                LocalDateTime.now());
        int allNum = jobLogService.count(qw);
        qw.eq(JobLogEntity::getStatus, JobConstants.FAIL);
        int errNum = jobLogService.count(qw);

        // 定时任务错误警报邮件：
        // 1.最近10次出现大于等于2次异常发出警报
        // 2.警报每天最多发一次。（00:00-08:00不发送）
        List<JobLogEntity> list = jobLogService.list(
                new LambdaQueryWrapper<JobLogEntity>()
                        .select(JobLogEntity::getStatus)
                        .orderByDesc(JobLogEntity::getId)
                        .last("LIMIT 10")
        );
        long x = list.stream().map(JobLogEntity::getStatus).filter(JobConstants.FAIL::equals).count();

        // 数据库容量信息(前5条) 2020-09-08关闭查询数据库信息，原因INFORMATION_SCHEMA视图信息不准确
//        List<HashMap<String, String>> dbInfo = taskMapper.getDBInfo(dbName, dbInfoLimit);

        StringBuilder sb = new StringBuilder();
        DecimalFormat numberFormat = new DecimalFormat("#,###");
        sb.append("<p>======== REPORT ========");
        sb.append("</p><p>※现在时间：" + DateUtil.dateFormat(null, DateUtil.HOUR_PATTERN) + "，系统已运行：" + DateUtil.diffDate(TM));
        if (isSysSuc) {
            double memRealUsed = Double.parseDouble(object.get("memRealUsed") + "");
            double memTotal = Double.parseDouble(object.get("memTotal") + "");
            double memoryPercent = (memRealUsed / memTotal) * 100D;
            sb.append("</p><p>※服务器信息：" + object.get("system") + "，已运行" + object.get("time"));
            sb.append("</p><p>  物理内存使用情况：" + memRealUsed + "/" + memTotal + "（" + new DecimalFormat("###.###").format(memoryPercent) + "%）");
        } else {
            sb.append("</p><p>※获取系统信息失败：" + object.get("msg"));
        }
        sb.append("</p><p>※巡警医生执行次数：" + numberFormat.format(count));
        sb.append("</p><p>※定时任务最近一天内执行次数：" + numberFormat.format(allNum) + "，其中错误次数：" + numberFormat.format(errNum));
        sb.append("</p><p>  定时任务最近10次执行错误数：" + x);
//        sb.append("</p><p>※数据库“" + dbName + "”容量信息（前" + dbInfoLimit + "条）");
//        for (HashMap<String, String> i : dbInfo) {
//            sb.append("</p><p>  " + i.toString());
//        }
        sb.append("</p><p>======== END ========</p>");

        // 达到条件发送邮件
        if (x >= 2 && isEmail()) {
            lastEmail = System.currentTimeMillis();
            String subject = "巡警医生报告邮件 from wgn API";
            String content = HtmlModel.mailBody("巡警医生报告邮件", sb.toString());
            EmailInfo emailInfo = new EmailInfo("wuguangnuo@qq.com", subject, content);
            EmailUtil.sendHtmlMail(emailInfo);
        }
        log.info(sb.toString().replaceAll("<p>", "\r\n").replaceAll("</p>", ""));
    }

    /**
     * 当前时间段是否可发送邮件
     * 1.距上一次大于24小时
     * 2.不在0:00-8:00时段
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

    /**
     * 获取系统基础统计
     *
     * @return
     */
    private String getSystemTotal() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String md5Sign = EncryptUtil.getMD5Str(btSign);
        String token = EncryptUtil.getMD5Str(timestamp + md5Sign);

        HashMap<String, String> map = new HashMap<>();
        map.put("request_time", timestamp);
        map.put("request_token", token);

        return HttpUtil.httpPostMap(map, SYSTEM_TOTAL);
    }
}
