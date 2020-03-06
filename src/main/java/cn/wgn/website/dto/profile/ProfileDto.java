package cn.wgn.website.dto.profile;

import cn.wgn.website.dto.BaseDto;
import io.swagger.annotations.ApiModelProperty;

/**
 * 个人信息
 *
 * @author WuGuangNuo
 * @date Created in 2020/3/5 16:08
 */
public class ProfileDto extends BaseDto {
    @ApiModelProperty(value = "登陆名")
    private String username;

    @ApiModelProperty(value = "姓名")
    private String realname;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "头像")
    private String headimg;

    @ApiModelProperty(value = "邮箱")
    private String email;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
