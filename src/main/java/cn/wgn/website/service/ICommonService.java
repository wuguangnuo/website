package cn.wgn.website.service;

import cn.wgn.website.dto.ApiRes;
import cn.wgn.website.dto.common.AccountLogin;

import javax.servlet.http.HttpServletRequest;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/18 17:35
 */
public interface ICommonService {
    /**
     * 账号密码登录
     *
     * @return
     */
    ApiRes loginByPd(AccountLogin accountLogin, HttpServletRequest request);
}
