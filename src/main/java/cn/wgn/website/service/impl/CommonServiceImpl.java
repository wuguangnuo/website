package cn.wgn.website.service.impl;

import cn.wgn.website.dto.ApiRes;
import cn.wgn.website.dto.common.AccountLogin;
import cn.wgn.website.dto.common.LoginData;
import cn.wgn.website.dto.utils.EmailInfo;
import cn.wgn.website.entity.UserEntity;
import cn.wgn.website.entity.CallEntity;
import cn.wgn.website.enums.RedisPrefixKeyEnum;
import cn.wgn.website.mapper.UserMapper;
import cn.wgn.website.mapper.CallMapper;
import cn.wgn.website.service.ICommonService;
import cn.wgn.website.utils.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private IpUtil ipUtil;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private CallMapper callMapper;

    /**
     * 账号密码登录
     *
     * @return
     */
    @Override
    public ApiRes loginByPd(AccountLogin accountLogin, HttpServletRequest request) {
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
        String redisValue = userEntity.getId() + ":" + userEntity.getUsername() + ":" + userEntity.getRoleid();
        // Redis : DB.Sys -> Id:No:RoleId
        redisUtil.set(token,
                RedisPrefixKeyEnum.Token.toString(),
                redisValue,
                WebSiteUtil.EXPIRE_TIME);

        LoginData loginData = new LoginData();
        BeanUtils.copyProperties(userEntity, loginData);
        loginData.setToken(token);

        userEntity.setLoginAt(LocalDateTime.now());
        userMapper.updateById(userEntity);

        // 检测IP并发出警告
        checkIp(redisValue, ipUtil.getIp(request), userEntity.getEmail());

        return ApiRes.suc("登录成功", loginData);
    }

    /**
     * 检测Ip并发出警告邮件
     *
     * @param us    用户
     * @param ip    IP
     * @param email 邮箱
     */
    private void checkIp(String us, int ip, String email) {
        if (Strings.isNullOrEmpty(email)) {
            return;
        }
        CallEntity callEntity = callMapper.selectOne(
                new QueryWrapper<CallEntity>().lambda()
                        .eq(CallEntity::getUs, us)
                        .orderByDesc(CallEntity::getId)
                        .last("LIMIT 1")
        );
        if (callEntity == null) {
            // 第一次登录
            EmailInfo emailInfo = new EmailInfo(
                    email,
                    "欢迎登录",
                    HtmlModel.mailBody("欢迎邮件", "<p>欢迎登录<strong style='color:red;font-size:20px;'>wuguangnuo.cn</strong>。</p>")
            );
            LOG.info("发送邮件：" + emailInfo.toString());
            emailUtil.sendHtmlMail(emailInfo);
        } else {
            if (callEntity.getIp() == ip) {
                // IP相同，放行
                return;
            }
            String city1 = ipUtil.getIpRegion(callEntity.getIp()).getCity();
            String city2 = ipUtil.getIpRegion(ip).getCity();
            if (city1.equals(city2) && !Strings.isNullOrEmpty(city1)) {
                // 同城且不空，放行
                return;
            }
            EmailInfo emailInfo = new EmailInfo(
                    email,
                    "IP警告",
                    HtmlModel.mailBody("IP异常警告邮件", "<p>本次登录IP：<span style='color:red'>" + ipUtil.int2ip(ip) + "</span>" + ipUtil.getIpRegion(ip).toString() + "，" +
                            "上次登录IP：<span style='color:red'>" + ipUtil.int2ip(callEntity.getIp()) + "</span>" + ipUtil.getIpRegion(callEntity.getIp()).toString() + "</p>")
            );
            LOG.info("发送邮件：" + emailInfo.toString());
            emailUtil.sendHtmlMail(emailInfo);
        }
    }
}
