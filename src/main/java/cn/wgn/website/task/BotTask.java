package cn.wgn.website.task;

import com.google.common.base.Strings;
import org.springframework.stereotype.Component;

/**
 * 爬虫定时任务
 *
 * @author WuGuangNuo
 * @date Created in 2020/5/10 16:36
 */
@Component("BotTask")
public class BotTask {
    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        System.out.println("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}" +
                s + ";" + b + ";" + l + ";" + d + ";" + i);
    }

    public void ryParams(String params) {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams() {
        System.out.println("@@@@@@@@!!!!!!!!执行无参方法");
    }
}
