package cn.wgn.website.task;

import cn.wgn.website.utils.DateUtil;
import com.sun.management.OperatingSystemMXBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时任务
 *
 * @author WuGuangNuo
 * @date Created in 2020/4/23 10:42
 */
@Slf4j
@Component
public class TestTask {
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final long timestamp = System.currentTimeMillis();

    @Autowired
    private DateUtil dateUtil;

    /**
     * 每隔半小时打印系统信息
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void reportCurrentTime() {
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long totalPhysicalMemorySize = osmxb.getTotalPhysicalMemorySize();
        long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();
        Double memoryPercent = (1 - (double) freePhysicalMemorySize / (double) totalPhysicalMemorySize) * 100D;
        log.info("REPORT：现在时间：" + df.format(new Date()) +
                " 系统已运行：" + dateUtil.diffDate(timestamp) +
                " 内存使用率：" + new DecimalFormat("###.###").format(memoryPercent) + "%");
    }
}
