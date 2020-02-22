package cn.wgn.website.service.impl;

import cn.wgn.website.dto.ApiRes;
import cn.wgn.website.dto.common.AccountLogin;
import cn.wgn.website.dto.common.LoginData;
import cn.wgn.website.entity.UserEntity;
import cn.wgn.website.enums.RedisPrefixKeyEnum;
import cn.wgn.website.mapper.UserMapper;
import cn.wgn.website.service.ICommonService;
import cn.wgn.website.utils.EncryptUtil;
import cn.wgn.website.utils.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/18 17:48
 */
@Slf4j
@Service
public class CommonServiceImpl implements ICommonService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EncryptUtil encryptUtil;

    // 账号过期时间,单位分
    private static final int expireTime = 9000000;

    /**
     * 账号密码登录
     *
     * @return
     */
    @Override
    public ApiRes loginByPd(AccountLogin accountLogin) {
        log.info("登录用户信息：" + accountLogin.toString());

        // 验证登录密码
        UserEntity userEntity = userMapper.selectOne(
                new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getUsername, accountLogin.getAccount())
        );
        if (userEntity == null) {
            return ApiRes.fail("账号不存在");
        }
        if (!userEntity.getPassword().equals(encryptUtil.getMD5Str(accountLogin.getPassword()))) {
            return ApiRes.fail("密码错误");
        }

        String token = UUID.randomUUID().toString().replaceAll("-", "");
        // Redis : DB.Sys -> Id:No:RoleId
        redisUtil.set(token, RedisPrefixKeyEnum.Sys.toString(), userEntity.getId() + ":" + userEntity.getUsername() + ":" + userEntity.getRoleid(), expireTime);

        LoginData loginData = new LoginData();
        BeanUtils.copyProperties(userEntity, loginData);
        loginData.setToken(token);

        userEntity.setLoginAt(LocalDateTime.now());
        userMapper.updateById(userEntity);

        return ApiRes.suc("登录成功", loginData);
    }
}
