package cn.wgn.website.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * WebSite 工具类
 *
 * @author WuGuangNuo
 * @date Created in 2020/2/16 21:42
 */
@Component
public class WebSiteUtil {
    // 最大分页
    public static final Page PAGE_MAX_SIZE = new Page(0, 200);
    // 默认分页
    public static final Page PAGE_DEFAULT_SIZE = new Page(0, 10);

    // Redis过期时间(秒)
    public static final int EXPIRE_TIME = 7 * 24 * 3600;

    /**
     * 格式化时间
     * 2020-01-01 20:20
     *
     * @param dt
     * @return
     */
    public static String dateFormat(LocalDateTime dt) {
        return dt.toString().replaceAll("T", " ").substring(0, 16);
    }

    /**
     * 简略信息
     *
     * @param s
     * @return
     */
    public static String cutContent(String s) {
        return cutContent(s, 300);
    }

    public static String cutContent(String s, int len) {
        if (s.length() <= len) {
            return s;
        }
        return s.substring(0, len)
//                .replaceAll("&", "&amp;")
//                .replaceAll("\"", "&quot;")
//                .replaceAll("'", " '")
//                .replaceAll("<", "&lt;")
//                .replaceAll(">", "&gt;")
                .replaceAll("\r", "")
                .replaceAll("\n", "") + "...";
    }

    /**
     * 分割字符串
     *
     * @param s string
     * @param l length
     * @return
     */
    public static String cutStr(String s, int l) {
        return s.substring(0, Math.min(s.length(), l));
    }

    /**
     * 获取随机字符串(UUID)
     *
     * @return
     */
    public static String randomStr() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 校验用户输入的 orderBy 字段
     *
     * @param orderBy
     * @param c
     * @return
     */
    public static String checkOrderBy(String orderBy, Class c) {
        if (!Strings.isNullOrEmpty(orderBy)) {
            String[] orderBys = orderBy.split(" ");
            if (orderBys.length != 2 || (!("ASC").equalsIgnoreCase(orderBys[1]) && !("DESC").equalsIgnoreCase(orderBys[1]))) {
                return "orderBy 输入有误,例(id asc)";
            }
            Field[] fields = c.getDeclaredFields();
            boolean t = false;
            for (Field field : fields) {
                if (orderBys[0].equalsIgnoreCase(field.getName())) {
                    t = true;
                }
            }
            if (!t) {
                return "orderBy 输入有误,排序字段必须属于输出字段";
            }
        }
        return "1";
    }

    /**
     * 对输入的范围调序
     * <p>注意：对象的属性顺序必须先1后2</p>
     *
     * @param dto
     */
    public static void sortDto(Object dto) {
        Field[] fields = dto.getClass().getDeclaredFields();

        try {
            Field field1 = null, field2;
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getName().endsWith("2")) {
                    field2 = field;
                    assert field1 != null;
                    if (field1.get(dto) != null && field2.get(dto) != null) {
                        if (field.getType().equals(Integer.class)) {
                            Integer var1 = (Integer) field1.get(dto);
                            Integer var2 = (Integer) field2.get(dto);

                            if (var1 > var2) {
                                field1.set(dto, var2);
                                field2.set(dto, var1);
                            }
                        } else if (field.getType().equals(Double.class)) {
                            Double var1 = (Double) field1.get(dto);
                            Double var2 = (Double) field2.get(dto);

                            if (var1 > var2) {
                                field1.set(dto, var2);
                                field2.set(dto, var1);
                            }
                        } else if (field.getType().equals(LocalDateTime.class)) {
                            LocalDateTime var1 = (LocalDateTime) field1.get(dto);
                            LocalDateTime var2 = (LocalDateTime) field2.get(dto);

                            if (var1.isAfter(var2)) {
                                field1.set(dto, var2);
                                field2.set(dto, var1);
                            }
                        } else {
                            String var1 = field1.get(dto).toString();
                            String var2 = field2.get(dto).toString();

                            if (var1.compareToIgnoreCase(var2) > 0) {
                                field1.set(dto, var2);
                                field2.set(dto, var1);
                            }
                        }
                    }
                }
                field1 = field;

                // 调整字段映射
                if ("orderBy".equals(field.getName())) {
                    String[] s = ((String) field.get(dto)).split(" ");
                    if (s.length == 2 && ("ASC".equalsIgnoreCase(s[1]) || "DESC".equalsIgnoreCase(s[1]))) {
                        char[] ss = s[0].toCharArray();
                        StringBuilder sb = new StringBuilder();
                        for (char c : ss) {
                            if (c < 97) {
                                sb.append("_");
                            }
                            sb.append(c);
                        }
                        field.set(dto, sb.toString() + " " + s[1]);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
