package cn.wgn.framework.web.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class AccountLogin extends BaseDto {
    @ApiModelProperty("账号")
    @NotBlank(message = "账号必填")
    private String account;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码必填")
    private String password;
}
