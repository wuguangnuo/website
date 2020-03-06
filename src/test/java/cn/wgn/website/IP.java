package cn.wgn.website;

/**
 * @author WuGuangNuo
 * @date Created in 2020/3/6 11:28
 */
public class IP {
    public static void main(String[] args) {

        String[] a = {"0.0.0.0",
                "192.168.1.1",
                "127.0.0.1",
                "120.36.226.41",
                "8.8.8.8",
                "114.114.0.1",
                "255.255.255.255"};

        for (String b : a) {
            System.out.println(b.equals(int2ip(ip2int(b))));
        }


    }

    private static Integer ip2int(String ip) {
        int ips = 0;
        String[] numbers = ip.split("[.]");
        for (int i = 0; i < 4; i++) {
            ips = ips << 8 | Integer.parseInt(numbers[i]);
        }
        return ips;
    }

    private static String int2ip(Integer number) {
        String ip = "";
        for (int i = 3; i >= 0; i--) {
            ip += String.valueOf((number >> 8 * i & 0xff));
            if (i != 0) {
                ip += ".";
            }
        }
        //120.36.226.41
        return ip;
    }
}
