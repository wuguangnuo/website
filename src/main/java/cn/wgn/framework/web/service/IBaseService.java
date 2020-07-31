package cn.wgn.framework.web.service;

import cn.wgn.framework.utils.TokenUtil;
import cn.wgn.framework.web.domain.UserData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author WuGuangNuo
 * @date Created in 2020/5/24 23:13
 */
public interface IBaseService<T> extends IService<T> {

    /**
     * 获取用户信息
     *
     * @return
     */
    default UserData getUserData() {
        return TokenUtil.getUserData();
    }

    /**
     * 获取用户名(轻量化)
     *
     * @return
     */
    default Long getUserId() {
        return TokenUtil.getUserId();
    }

    /**
     * 获取用户名(轻量化)
     *
     * @return
     */
    default String getUsername() {
        return TokenUtil.getUserName();
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
