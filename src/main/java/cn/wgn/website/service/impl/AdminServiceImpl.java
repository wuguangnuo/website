package cn.wgn.website.service.impl;

import cn.wgn.website.service.IAdminService;
import org.springframework.stereotype.Service;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/18 17:37
 */
@Service
public class AdminServiceImpl implements IAdminService {
    /**
     * 测试
     *
     * @return
     */
    @Override
    public String test() {
        return getUserData().toString();
    }
}
