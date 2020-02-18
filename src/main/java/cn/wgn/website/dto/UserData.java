package cn.wgn.website.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 登录用户
 */
@Data
@Accessors(chain = true)
public class UserData {
    @ApiModelProperty(value = "员工ID")
    private int id;
    @ApiModelProperty(value = "员工账号")
    private String account;
    @ApiModelProperty(value = "权限ID")
    private int roleId;
}
