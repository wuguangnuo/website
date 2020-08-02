package cn.wgn.framework.web.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * 用户信息，用于Redis缓存
 * Jwt -> uuid -> UserData
 *
 * @author WuGuangNuo
 * @date Created in 2020/2/18 17:52
 */
@Data
@Accessors(chain = true)
public class UserData extends BaseDto {
    @ApiModelProperty(value = "fast simple UUID")
    private transient String uuid;

    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "登陆名")
    private String username;

    @ApiModelProperty(value = "姓名")
    private String realname;

    @ApiModelProperty(value = "权限id")
    private Integer roleid;

    @ApiModelProperty(value = "权限列表")
    private Set<String> permissions;

    @ApiModelProperty(value = "头像")
    private String headimg;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "最后一次登录时间")
    private LocalDateTime lastLogin;
}
