package cn.wgn.framework.utils.job;

import cn.wgn.framework.utils.DateUtil;
import org.quartz.CronExpression;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * cron表达式工具类
 *
 * @author WuGuangNuo
 * @date Created in 2020/6/14 21:37
 */
public class CronUtil {
    /**
     * 返回一个布尔值代表一个给定的Cron表达式的有效性
     *
     * @param cronExpression Cron表达式
     * @return boolean 表达式是否有效
     */
    public static boolean isValid(String cronExpression) {
        return CronExpression.isValidExpression(cronExpression);
    }

    /**
     * 返回下一个执行时间根据给定的Cron表达式
     *
     * @param cronExpression Cron表达式
     * @return Date 下次Cron表达式执行时间
     */
    public static Date getNextExecution(String cronExpression) {
        try {
            CronExpression cron = new CronExpression(cronExpression);
            return cron.getNextValidTimeAfter(new Date(System.currentTimeMillis()));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * 获得Cron表达式将来x次执行结果
     *
     * @param cronExpression Cron表达式
     * @param x              执行x次，默认5次
     * @return
     */
    public static List<String> getNextX(String cronExpression, Integer x) {
        if ((x = x == null ? 5 : x) > 0) {
            CronExpression cron;
            try {
                cron = new CronExpression(cronExpression);
            } catch (ParseException e) {
                return null;
            }
            Date date = new Date();
            List<String> list = new ArrayList<>();
            for (int i = 0; i < x; i++) {
                date = cron.getNextValidTimeAfter(date);
                list.add(DateUtil.dateFormat(date, DateUtil.DATE_TIME_PATTERN));
            }
            return list;
        } else {
            return null;
        }
    }
}
