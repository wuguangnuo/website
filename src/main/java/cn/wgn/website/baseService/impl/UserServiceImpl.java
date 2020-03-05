package cn.wgn.website.baseService.impl;

import cn.wgn.website.entity.UserEntity;
import cn.wgn.website.mapper.UserMapper;
import cn.wgn.website.baseService.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-03-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements IUserService {

}
