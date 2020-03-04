package cn.wgn.website;

import org.springframework.web.client.RestTemplate;

import java.util.Calendar;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/19 18:00
 */
public class YinWang {
    public static void main(String[] args) {
        f();
    }

    public static void f() {

        RestTemplate restTemplate = new RestTemplate();
        Notice notice = restTemplate.getForObject("http://ip.ws.126.net/ipquery?ip=8.8.8.8"
                , Notice.class);
        System.out.println(notice.toString());
    }
}

class Notice {
    private String city;
    private String province;

    @Override
    public String toString() {
        return "Notice{" +
                "city='" + city + '\'' +
                ", province='" + province + '\'' +
                '}';
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}