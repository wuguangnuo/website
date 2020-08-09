package cn.wgn.framework.web.domain;

import io.swagger.annotations.ApiModelProperty;

/**
 * 首页信息
 *
 * @author WuGuangNuo
 * @date Created in 2020/7/8 21:50
 */
public class HomeInfo extends BaseDto {
    @ApiModelProperty("头像")
    private String headimg;
    @ApiModelProperty("登录名")
    private String username;
    @ApiModelProperty("姓名")
    private String realname;
//    @ApiModelProperty("身份")
//    private String role;
    @ApiModelProperty("最后一次登陆时间")
    private String loginTime;
    @ApiModelProperty("登录IP")
    private String lastIp;
    @ApiModelProperty("登录地点")
    private String lastAdd;

    @ApiModelProperty("今日接口访问量")
    private Integer todayNum;
    @ApiModelProperty("接口总访问量")
    private Integer allNum;

    @ApiModelProperty("本周访问统计")
    private String weekChart;

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    public String getLastAdd() {
        return lastAdd;
    }

    public void setLastAdd(String lastAdd) {
        this.lastAdd = lastAdd;
    }

    public Integer getTodayNum() {
        return todayNum;
    }

    public void setTodayNum(Integer todayNum) {
        this.todayNum = todayNum;
    }

    public Integer getAllNum() {
        return allNum;
    }

    public void setAllNum(Integer allNum) {
        this.allNum = allNum;
    }

    public String getWeekChart() {
        return weekChart;
    }

    public void setWeekChart(String weekChart) {
        this.weekChart = weekChart;
    }
}
