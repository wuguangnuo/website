package cn.wgn.framework.web.service.impl;

import cn.wgn.framework.constant.Constants;
import cn.wgn.framework.utils.*;
import cn.wgn.framework.utils.ip.IpUtil;
import cn.wgn.framework.utils.mail.EmailInfo;
import cn.wgn.framework.utils.mail.EmailUtil;
import cn.wgn.framework.web.ApiRes;
import cn.wgn.framework.web.domain.AccountLogin;
import cn.wgn.framework.web.domain.LoginData;
import cn.wgn.framework.web.domain.ProfileDto;
import cn.wgn.framework.web.entity.UserEntity;
import cn.wgn.framework.web.entity.VisitorEntity;
import cn.wgn.framework.web.enums.RedisPrefixKeyEnum;
import cn.wgn.framework.web.mapper.UserMapper;
import cn.wgn.framework.web.service.IUserService;
import cn.wgn.framework.web.service.IVisitorService;
import cn.wgn.website.utils.CosClientUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-27
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, UserEntity> implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CosClientUtil cosClientUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IpUtil ipUtil;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private IVisitorService visitorService;


    /**
     * 账号密码登录
     *
     * @param accountLogin
     * @param request
     * @return
     */
    @Override
    public ApiRes loginByPd(AccountLogin accountLogin, HttpServletRequest request) {
        LOG.info("登录用户信息：" + accountLogin.toString());

        // 验证登录密码
        UserEntity userEntity = this.getOne(
                new QueryWrapper<UserEntity>().lambda()
                        .eq(UserEntity::getUsername, accountLogin.getAccount())
        );
        if (userEntity == null) {
            return ApiRes.fail("账号不存在");
        }
        if (!userEntity.getPassword().equals(EncryptUtil.encryptString(accountLogin.getPassword()))) {
            return ApiRes.fail("密码错误");
        }

        String token = IdUtil.simpleUUID();
        String redisValue = userEntity.getId() + ":" + userEntity.getUsername() + ":" + userEntity.getRoleid();
        // Redis : DB.Sys -> Id:No:RoleId
        redisUtil.set(token,
                RedisPrefixKeyEnum.TOKEN.getValue(),
                redisValue,
                Constants.WEEK_EXPIRE);

        LoginData loginData = new LoginData();
        BeanUtils.copyProperties(userEntity, loginData);
        loginData.setToken(token);

        userEntity.setLoginTime(LocalDateTime.now());
        this.updateById(userEntity);

        // 检测IP并发出警告
        checkIp(redisValue, IpUtil.getIp(request), userEntity.getEmail());

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
        if (StringUtil.isNullOrEmpty(email)) {
            return;
        }
        VisitorEntity visitorEntity = visitorService.getOne(
                new QueryWrapper<VisitorEntity>().lambda()
                        .eq(VisitorEntity::getUs, us)
                        .orderByDesc(VisitorEntity::getId),
                false
        );
        if (visitorEntity == null) {
            // 第一次登录
            EmailInfo emailInfo = new EmailInfo(
                    email,
                    "欢迎登录",
                    HtmlModel.mailBody("欢迎邮件", "<p>欢迎登录<strong style='color:red;font-size:20px;'>wuguangnuo.cn</strong>。</p>")
            );
            LOG.info("发送邮件：" + emailInfo.toString());
            emailUtil.sendHtmlMail(emailInfo);
        } else {
            if (visitorEntity.getIp() == ip) {
                // IP相同，放行
                return;
            }
            String city1 = ipUtil.getIpRegion(visitorEntity.getIp()).getCity();
            String city2 = ipUtil.getIpRegion(ip).getCity();
            if (city1.equals(city2) && !Strings.isNullOrEmpty(city1)) {
                // 同城且不空，放行
                return;
            }
            EmailInfo emailInfo = new EmailInfo(
                    email,
                    "IP警告",
                    HtmlModel.mailBody("IP异常警告邮件", "<p>本次登录IP：<span style='color:red'>" + IpUtil.int2ip(ip) + "</span>" + ipUtil.getIpRegion(ip).toString() + "，" +
                            "上次登录IP：<span style='color:red'>" + IpUtil.int2ip(visitorEntity.getIp()) + "</span>" + ipUtil.getIpRegion(visitorEntity.getIp()).toString() + "</p>")
            );
            LOG.info("发送邮件：" + emailInfo.toString());
            emailUtil.sendHtmlMail(emailInfo);
        }
    }

    /**
     * 获取个人信息
     *
     * @return
     */
    @Override
    public ProfileDto getProfile() {
        UserEntity entity = this.getById(getUserData().getId());
        ProfileDto result = new ProfileDto();
        BeanUtils.copyProperties(entity, result);
        result.setPassword("");
        return result;
    }

    /**
     * 更新个人信息
     *
     * @param dto
     * @return "1"
     */
    @Override
    public String updateProfile(ProfileDto dto) {
        if (!StringUtil.isNullOrEmpty(dto.getHeadimg())) {
            String url;
            if (dto.getHeadimg().startsWith("http")) {
                url = dto.getHeadimg();
            } else {
                if (dto.getHeadimg().split(",").length != 2) {
                    return "图片需要含URI";
                }
                url = this.uploadHeadimg(dto.getHeadimg());
            }
            dto.setHeadimg(url);
        }

        if (!StringUtil.isNullOrEmpty(dto.getPassword())) {
            dto.setPassword(EncryptUtil.encryptString(dto.getPassword()));
        }

        int res = userMapper.updateButNull(dto, getUserData().getId());
        return res == 1 ? "1" : "更新错误";
    }

    /**
     * 更新头像
     *
     * @param img 图片base64(包含URI)
     * @return url
     */
    @Override
    public String updateHeadImg(String img) {
        if (img.split(",").length != 2) {
            return "图片需要含URI";
        }

        String url = this.uploadHeadimg(img);

        UserEntity userEntity = this.getById(getUserData().getId());
        userEntity.setHeadimg(url);
        this.updateById(userEntity);

        return url;
    }

    /**
     * 上传压缩头像
     *
     * @param base64 图片base64
     * @return url
     */
    private String uploadHeadimg(String base64) {
        // todo 用户头像图片压缩
//        String[] imgUrls = base64.split(",");
//        base64 = "data:image/jpeg;base64,";
//        if (base64.length() < 100 * 1000) {
//            base64 += thumbnailsUtil.imageCompress(imgUrls[1], 1);
//        } else if (base64.length() < 500 * 1000) {
//            base64 += thumbnailsUtil.imageCompress(imgUrls[1], 0.5);
//        } else if (base64.length() < 1000 * 1000) {
//            base64 += thumbnailsUtil.imageCompress(imgUrls[1], 0.3);
//        } else {
//            base64 += thumbnailsUtil.imageCompress(imgUrls[1], 0.2);
//        }
        MultipartFile file = FileUtil.base64ToMultipart(base64);

        String url = cosClientUtil.uploadFile2Cos(file, "headimg");
        return url;
    }
}
