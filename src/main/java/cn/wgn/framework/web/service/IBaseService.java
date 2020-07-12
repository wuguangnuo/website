package cn.wgn.framework.web.service;

import cn.wgn.framework.web.domain.UserData;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * @author WuGuangNuo
 * @date Created in 2020/5/24 23:13
 */
public interface IBaseService<T> extends IService<T> {
    /**
     * 获取登录用户信息
     *
     * @return
     */
    default UserData getUserData() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        UserData userData = (UserData) request.getAttribute("userData");
        return userData;
    }

    /**
     * 随机排序所有数据
     *
     * @return
     */
    List<T> randList();

    /**
     * 随机排序所有数据+限制数量
     *
     * @return
     */
    List<T> randList(int limit);
}
