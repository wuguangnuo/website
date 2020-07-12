package cn.wgn.framework.web.domain;

/**
 * 用户数据
 *
 * @author WuGuangNuo
 * @date Created in 2020/6/7 11:55
 */
public class UserData extends BaseDto {
    /**
     * 员工ID
     */
    private Long id;
    /**
     * 员工账号
     */
    private String account;
    /**
     * 权限ID
     */
    private Long roleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}

