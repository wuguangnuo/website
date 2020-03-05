package cn.wgn.website.dto.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 登录成功返回信息
 *
 * @author WuGuangNuo
 * @date Created in 2020/2/18 17:52
 */
@Data
@Accessors(chain = true)
public class LoginData {
    @ApiModelProperty(value = "Token")
    private String token;

    @ApiModelProperty(value = "登陆名")
    private String username;

    @ApiModelProperty(value = "姓名")
    private String realname;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "权限默认0")
    private Integer roleid;

    @ApiModelProperty(value = "头像")
    private String headimg;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "最后一次修改时间")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "最后一次登陆时间")
    private LocalDateTime loginAt;
}
