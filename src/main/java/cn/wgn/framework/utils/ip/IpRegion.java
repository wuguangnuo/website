package cn.wgn.framework.utils.ip;

/**
 * IP 地理位置
 *
 * @author WuGuangNuo
 * @date Created in 2020/3/7 10:55
 */
public class IpRegion {
    // 国家
    private String country;
    // 大区
    private String area;
    // 省份
    private String province;
    // 城市
    private String city;
    // 运营商
    private String isp;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    @Override
    public String toString() {
        return "{" +
                "country='" + country + '\'' +
                ", area='" + area + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", isp='" + isp + '\'' +
                '}';
    }
}
