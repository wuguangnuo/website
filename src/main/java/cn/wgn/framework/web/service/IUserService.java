package cn.wgn.framework.web.service;

import cn.wgn.framework.web.ApiRes;
import cn.wgn.framework.web.domain.AccountLogin;
import cn.wgn.framework.web.entity.UserEntity;
import cn.wgn.framework.web.domain.ProfileDto;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-27
 */
public interface IUserService extends IBaseService<UserEntity> {
    /**
     * 账号密码登录
     *
     * @param accountLogin
     * @param request
     * @return
     */
    ApiRes loginByPd(AccountLogin accountLogin, HttpServletRequest request);

    /**
     * 获取个人信息
     *
     * @return
     */
    ProfileDto getProfile();

    /**
     * 更新个人信息
     *
     * @param dto
     * @return "1"
     */
    String updateProfile(ProfileDto dto);

    /**
     * 更新头像
     *
     * @param img 图片base64(包含URI)
     * @return url
     */
    String updateHeadImg(String img);
}
