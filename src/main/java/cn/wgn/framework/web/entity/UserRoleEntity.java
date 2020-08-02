package cn.wgn.framework.web.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import cn.wgn.framework.web.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户角色表
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wu_user_role")
@ApiModel(value="UserRoleEntity对象", description="用户角色表")
public class UserRoleEntity extends BaseEntity<UserRoleEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
