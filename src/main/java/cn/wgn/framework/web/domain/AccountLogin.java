package cn.wgn.framework.web.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 登录信息
 *
 * @author WuGuangNuo
 */
public class AccountLogin extends BaseDto {
    @ApiModelProperty("账号")
    @NotBlank(message = "账号必填")
    private String account;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码必填")
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
