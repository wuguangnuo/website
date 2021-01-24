package cn.wgn.framework.web.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-07
 */

@TableName("wu_role")
@ApiModel(value="RoleEntity对象", description="角色表")
public class RoleEntity extends BaseEntity<RoleEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色描述")
    private String roleDescription;

    @ApiModelProperty(value = "状态：1有效；0失效")
    private Integer roleStatus;


    @Override
    protected Serializable pkVal() {
        return null;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Integer getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(Integer roleStatus) {
        this.roleStatus = roleStatus;
    }
}
