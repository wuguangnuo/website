package cn.wgn.website;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/19 18:00
 */
public class YinWang {
    public static void main(String[] args) {
        f();
    }

    public static void f() {
        String[] a = new String[2];
        Object[] b = a;
        a[0] = "hi";
        b[1] = Integer.valueOf(42);
    }
}
