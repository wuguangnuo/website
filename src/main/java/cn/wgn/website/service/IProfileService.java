package cn.wgn.website.service;

import cn.wgn.website.dto.ApiRes;
import cn.wgn.website.dto.common.AccountLogin;
import cn.wgn.website.dto.profile.MenuTree;

import java.util.List;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/21 21:12
 */
public interface IProfileService extends IBaseService {
    /**
     * 账号密码登录
     *
     * @return
     */
    ApiRes loginByPd(AccountLogin accountLogin);

    /**
     * 获取树形菜单
     *
     * @return
     */
    List<MenuTree> getMenuTree();
}
