package cn.wgn.website.sys.task;

import cn.wgn.framework.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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

    /**
     * 巡警医生
     * 定时任务出错时发送邮件
     */
    public void doctor() {
        count++;
        System.out.println("巡警医生开始执行,次数: " + count);
        if ((count & 0xff) == 0) {
            System.out.println("“GOOD TEN TIMES”");
            throw new CommonException("DOCTOR 故意抛错,每运行0xff次抛错", 233);
        }
    }
}
