package cn.wgn.website.dto.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class AccountLogin {
    @ApiModelProperty("密码")
    @NotBlank(message = "密码必填")
    private String password;

    @ApiModelProperty("账号")
    @NotBlank(message = "账号必填")
    private String account;
}
