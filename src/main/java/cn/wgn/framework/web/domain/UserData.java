package cn.wgn.framework.web.domain;

import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * 用户信息，用于Redis缓存
 * Jwt -> uuid -> UserData
 *
 * @author WuGuangNuo
 * @date Created in 2020/2/18 17:52
 */
public class UserData extends BaseDto {
    @ApiModelProperty(value = "fast simple UUID")
    private transient String uuid;

    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "登陆名")
    private String username;

    @ApiModelProperty(value = "姓名")
    private String realname;

    @ApiModelProperty(value = "职位列表")
    private Set<Long> position;

    @ApiModelProperty(value = "权限列表")
    private Set<Long> permissions;

    @ApiModelProperty(value = "菜单列表")
    private List<String> menuList;

    @ApiModelProperty(value = "头像")
    private String headimg;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "最后一次登录时间")
    private LocalDateTime lastLogin;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Set<Long> getPosition() {
        return position;
    }

    public void setPosition(Set<Long> position) {
        this.position = position;
    }

    public Set<Long> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Long> permissions) {
        this.permissions = permissions;
    }

    public List<String> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<String> menuList) {
        this.menuList = menuList;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
}
