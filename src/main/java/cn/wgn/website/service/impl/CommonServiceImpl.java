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
import cn.wgn.website.utils.WebSiteUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/18 17:48
 */
@Service("commonService")
public class CommonServiceImpl extends BaseServiceImpl implements ICommonService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EncryptUtil encryptUtil;

    /**
     * 账号密码登录
     *
     * @return
     */
    @Override
    public ApiRes loginByPd(AccountLogin accountLogin) {
        LOG.info("登录用户信息：" + accountLogin.toString());

        // 验证登录密码
        UserEntity userEntity = userMapper.selectOne(
                new QueryWrapper<UserEntity>().lambda()
                        .eq(UserEntity::getUsername, accountLogin.getAccount())
        );
        if (userEntity == null) {
            return ApiRes.fail("账号不存在");
        }
        if (!userEntity.getPassword().equals(encryptUtil.encryptString(accountLogin.getPassword()))) {
            return ApiRes.fail("密码错误");
        }

        String token = WebSiteUtil.randomStr();
        // Redis : DB.Sys -> Id:No:RoleId
        redisUtil.set(token,
                RedisPrefixKeyEnum.Token.toString(),
                userEntity.getId() + ":" + userEntity.getUsername() + ":" + userEntity.getRoleid(),
                WebSiteUtil.EXPIRE_TIME);

        LoginData loginData = new LoginData();
        BeanUtils.copyProperties(userEntity, loginData);
        loginData.setToken(token);

        userEntity.setLoginAt(LocalDateTime.now());
        userMapper.updateById(userEntity);

        return ApiRes.suc("登录成功", loginData);
    }
}
