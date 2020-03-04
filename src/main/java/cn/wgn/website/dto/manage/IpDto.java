package cn.wgn.website.dto.manage;

import io.swagger.annotations.ApiModelProperty;

/**
 * 用户IP地址
 *
 * @author WuGuangNuo
 * @date Created in 2020/3/3 17:23
 */
public class IpDto {
    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("IP")
    private String ip;

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
