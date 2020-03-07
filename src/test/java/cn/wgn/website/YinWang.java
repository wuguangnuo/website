package cn.wgn.website;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/19 18:00
 */
public class YinWang {
    public static void main(String[] args) {
        f();
    }

    public static void f() {
        Map<String, Map<String, Integer>> data = new HashMap<String, Map<String, Integer>>();

        data.forEach((k, v) -> {
            System.out.println(k);
            System.out.println(v.toString());
        });
    }
}
