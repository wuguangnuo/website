package cn.wgn.framework.web.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 角色权限表
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-21
 */

@TableName("wu_role_menu")
@ApiModel(value="RoleMenuEntity对象", description="角色权限表")
public class RoleMenuEntity extends BaseEntity<RoleMenuEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "权限id")
    private Long menuId;

    @ApiModelProperty(value = "权限Code")
    private String menuCode;

    @Override
    protected Serializable pkVal() {
        return null;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }
}
