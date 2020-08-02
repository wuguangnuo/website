package cn.wgn.framework.web.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wu_user")
@ApiModel(value="UserEntity对象", description="用户表")
public class UserEntity extends BaseEntity<UserEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "登陆名")
    private String username;

    private String realname;

    @ApiModelProperty(value = "密码MD5")
    private String password;

    private String headimg;

    private String email;

    @ApiModelProperty(value = "最后一次登陆时间")
    private LocalDateTime loginTime;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
