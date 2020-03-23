package cn.wgn.website.dto.home;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author WuGuangNuo
 * @date Created in 2020/3/23 14:24
 */
public class VistorTable {
    @ApiModelProperty(value = "编号")
    private Long id;
    @ApiModelProperty(value = "受访页面")
    private String lk;
    @ApiModelProperty(value = "IP地址")
    private String ip;
    @ApiModelProperty(value = "地区")
    private String ad;
    @ApiModelProperty(value = "ISP")
    private String isp;
    @ApiModelProperty(value = "操作系统")
    private String os;
    @ApiModelProperty(value = "浏览器")
    private String br;
    @ApiModelProperty(value = "时间")
    private String tm;
    @ApiModelProperty(value = "代理")
    private String ag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLk() {
        return lk;
    }

    public void setLk(String lk) {
        this.lk = lk;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getBr() {
        return br;
    }

    public void setBr(String br) {
        this.br = br;
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public String getAg() {
        return ag;
    }

    public void setAg(String ag) {
        this.ag = ag;
    }
}
