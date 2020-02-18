package cn.wgn.website.service;

import cn.wgn.website.dto.UserData;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public interface IBaseService {
    /**
     * 获取登录用户信息
     *
     * @return
     */
    default UserData getUserData() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        UserData userData = (UserData) request.getAttribute("userData");
        return userData;
    }
}
