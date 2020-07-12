package cn.wgn.website.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * WebSite 工具类
 *
 * @author WuGuangNuo
 * @date Created in 2020/2/16 21:42
 */
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

    /**
     * 获取操作系统
     *
     * @param ag   user-agent
     * @param list 不存在的用others
     * @return
     */
    public static String getSystem(String ag, List<String> list) {
        if (Strings.isNullOrEmpty(ag)) {
            return "others";
        }
        String os;
        if (Pattern.matches("(?i)(.*)sitemap(.*)", ag) || Pattern.matches("(?i)(.*)Parser(.*)", ag)) {
            os = "sitemap";
        } else if (Pattern.matches("(?i)(.*)spider(.*)", ag) || Pattern.matches("(?i)(.*)bot(.*)", ag)) {
            os = "spider";
        } else if (Pattern.matches("(?i)(.*)win(.*)", ag) && Pattern.matches("(?i)(.*)[^.\\d]95(.*)", ag)) {
            os = "Windows 95";
        } else if (Pattern.matches("(?i)(.*)win(.*)", ag) && Pattern.matches("(?i)(.*)[^.\\d]98(.*)", ag)) {
            os = "Windows 98";
        } else if (Pattern.matches("(?i)(.*)win(.*)", ag) && Pattern.matches("(?i)(.*)nt\\s*5.0(.*)", ag)) {
            os = "Windows 2000";
        } else if (Pattern.matches("(?i)(.*)win(.*)", ag) && Pattern.matches("(?i)(.*)nt\\s*5.1(.*)", ag)) {
            os = "Windows XP";
        } else if (Pattern.matches("(?i)(.*)win(.*)", ag) && Pattern.matches("(?i)(.*)nt\\s*5.2(.*)", ag)) {
            os = "Windows XP";
        } else if (Pattern.matches("(?i)(.*)win(.*)", ag) && Pattern.matches("(?i)(.*)nt\\s*6.0(.*)", ag)) {
            os = "Windows Vista";
        } else if (Pattern.matches("(?i)(.*)win(.*)", ag) && Pattern.matches("(?i)(.*)nt\\s*6.1(.*)", ag)) {
            os = "Windows 7";
        } else if (Pattern.matches("(?i)(.*)win(.*)", ag) && Pattern.matches("(?i)(.*)nt\\s*6.2(.*)", ag)) {
            os = "Windows 8";
        } else if (Pattern.matches("(?i)(.*)win(.*)", ag) && Pattern.matches("(?i)(.*)nt\\s*10.0(.*)", ag)) {
            os = "Windows 10";
        } else if (Pattern.matches("(?i)(.*)win(.*)", ag) && Pattern.matches("(?i)(.*)nt(.*)", ag)) {
            os = "Windows NT";
        } else if (Pattern.matches("(?i)(.*)android\\s*([\\d\\.]+)(.*)", ag)) {
            os = "Android";
        } else if (Pattern.matches("(?i)(.*)iphone(.*)", ag)) {
            os = "iPhone";
        } else if (Pattern.matches("(?i)(.*)ipad(.*)", ag)) {
            os = "iPad";
        } else if (Pattern.matches("(?i)(.*)linux(.*)", ag)) {
            os = "Linux";
        } else if (Pattern.matches("(?i)(.*)unix(.*)", ag)) {
            os = "Unix";
        } else if (Pattern.matches("(?i)(.*)sun(.*)", ag) && Pattern.matches("(?i)(.*)os(.*)", ag)) {
            os = "SunOS";
        } else if (Pattern.matches("(?i)(.*)ibm(.*)", ag) && Pattern.matches("(?i)(.*)os(.*)", ag)) {
            os = "IBM OS/2";
        } else if (Pattern.matches("(?i)(.*)Mac(.*)", ag) && Pattern.matches("(?i)(.*)PC(.*)", ag)) {
            os = "Macintosh";
        } else if (Pattern.matches("(?i)(.*)Macintosh(.*)", ag) && Pattern.matches("(?i)(.*)Mac(.*)", ag)) {
            os = "MacOS";
        } else if (Pattern.matches("(?i)(.*)PowerPC(.*)", ag)) {
            os = "PowerPC";
        } else if (Pattern.matches("(?i)(.*)AIX(.*)", ag)) {
            os = "AIX";
        } else if (Pattern.matches("(?i)(.*)HPUX(.*)", ag)) {
            os = "HPUX";
        } else if (Pattern.matches("(?i)(.*)NetBSD(.*)", ag)) {
            os = "NetBSD";
        } else if (Pattern.matches("(?i)(.*)BSD(.*)", ag)) {
            os = "BSD";
        } else if (Pattern.matches("(?i)(.*)OSF1(.*)", ag)) {
            os = "OSF1";
        } else if (Pattern.matches("(?i)(.*)IRIX(.*)", ag)) {
            os = "IRIX";
        } else if (Pattern.matches("(?i)(.*)FreeBSD(.*)", ag)) {
            os = "FreeBSD";
        } else if (Pattern.matches("(?i)(.*)teleport(.*)", ag)) {
            os = "teleport";
        } else if (Pattern.matches("(?i)(.*)flashget(.*)", ag)) {
            os = "flashget";
        } else if (Pattern.matches("(?i)(.*)webzip(.*)", ag)) {
            os = "webzip";
        } else if (Pattern.matches("(?i)(.*)offline(.*)", ag)) {
            os = "offline";
        } else {
            os = "others";
        }
        if (list == null || list.size() == 0) {
            return os;
        } else {
            if (list.contains(os)) {
                return os;
            } else {
                return "others";
            }
        }
    }

    /**
     * 获取客户端类型
     *
     * @param ag   user-agent
     * @param list 不存在的用others
     * @return
     */
    public static String getBrowser(String ag, List<String> list) {
        if (Strings.isNullOrEmpty(ag)) {
            return "others";
        }
        String br, tmp;
        if (!Strings.isNullOrEmpty(tmp = getFirstMatcher("(?i)sitemap", ag)) ||
                !Strings.isNullOrEmpty(tmp = getFirstMatcher("(?i)Parser", ag))) {
            br = "sitemap";
        } else if (!Strings.isNullOrEmpty(tmp = getFirstMatcher("(?i)spider", ag)) ||
                !Strings.isNullOrEmpty(tmp = getFirstMatcher("(?i)bot", ag))) {
            br = "spider";
        } else if (!Strings.isNullOrEmpty(tmp = getFirstMatcher("(?i)Firefox\\/([^;)]+)+", ag))) {
            br = tmp;
        } else if (!Strings.isNullOrEmpty(tmp = getFirstMatcher("(?i)Maxthon\\/([\\d\\.]+)", ag))) {
            br = tmp;
        } else if (!Strings.isNullOrEmpty(tmp = getFirstMatcher("(?i)MSIE\\s*([^;)]+)+", ag))) {
            br = tmp.replace("MSIE", "IE");
        } else if (!Strings.isNullOrEmpty(tmp = getFirstMatcher("(?i)OPR\\/([\\d\\.]+)", ag))) {
            br = tmp.replace("OPR", "Opera");
        } else if (!Strings.isNullOrEmpty(tmp = getFirstMatcher("(?i)Edge\\/([\\d\\.]+)", ag))) {
            br = tmp;
        } else if (!Strings.isNullOrEmpty(tmp = getFirstMatcher("(?i)Chrome\\/([\\d\\.]+)", ag))) {
            br = tmp;
        } else if (!Strings.isNullOrEmpty(tmp = getFirstMatcher("(?i)Safari\\/([\\d\\.]+)", ag))) {
            br = tmp;
        } else if (!Strings.isNullOrEmpty(tmp = getFirstMatcher("(?i)rv:([\\d\\.]+)", ag))) {
            br = tmp.replace("rv:", "IE");
        } else {
            br = "others";
        }
        if (list == null || list.size() == 0) {
            return br;
        } else {
            for (String i : list) {
                if (br.startsWith(i)) {
                    return i;
                }
            }
            return "others";
        }
    }

    /**
     * 获取正则匹配第一个结果
     *
     * @param regex 正则表达式
     * @param str   待匹配字符串
     * @return 匹配到的部分
     */
    private static String getFirstMatcher(String regex, String str) {
        Matcher m = Pattern.compile(regex).matcher(str);
        if (m.find()) {
            return m.group(0);
        } else {
            return "";
        }
    }

    /**
     * 正则匹配，模糊匹配
     *
     * @param reges 正则表达式
     * @param str   待匹配字符串
     * @return 是否匹配
     */
    private static boolean isMatches(String reges, String str) {
        return Pattern.matches("(.*)" + reges + "(.*)", str);
    }
}
