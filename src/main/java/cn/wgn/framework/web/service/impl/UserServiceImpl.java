package cn.wgn.framework.web.service.impl;

import cn.wgn.framework.utils.*;
import cn.wgn.framework.utils.ip.IpUtil;
import cn.wgn.framework.utils.mail.EmailInfo;
import cn.wgn.framework.utils.mail.EmailUtil;
import cn.wgn.framework.web.ApiRes;
import cn.wgn.framework.web.domain.AccountLogin;
import cn.wgn.framework.web.domain.ProfileDto;
import cn.wgn.framework.web.domain.UserData;
import cn.wgn.framework.web.entity.*;
import cn.wgn.framework.web.mapper.UserMapper;
import cn.wgn.framework.web.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private IpUtil ipUtil;
    @Autowired
    private IVisitorService visitorService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRoleMenuService roleMenuService;
    @Autowired
    private IMenuService menuService;


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

        userEntity.setLoginTime(LocalDateTime.now());
        this.updateById(userEntity);

        UserData userData = buildUserData(userEntity);
        String token = TokenUtil.createToken(userData);

        // 检测IP并发出警告
        checkIp(userEntity.getId() + ":" + userEntity.getUsername(), IpUtil.getIp(request), userEntity.getEmail());

        userData.setUuid(token);
        return ApiRes.suc("登录成功", userData);
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
                        .gt(VisitorEntity::getTm, DateUtil.dateAddMonths(null, -1)) // 加快查询速度，限制一个月
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
            EmailUtil.sendHtmlMail(emailInfo);
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
            EmailUtil.sendHtmlMail(emailInfo);
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

    /**
     * 创建 UserData 对象
     *
     * @param entity UserEntity
     * @return
     */
    private UserData buildUserData(UserEntity entity) {
        List<UserRoleEntity> userRoleList = userRoleService.list(
                new QueryWrapper<UserRoleEntity>().lambda()
                        .eq(UserRoleEntity::getUserId, entity.getId())
        );
        // 所有职位的id
        Set<Long> position = userRoleList.stream().map(UserRoleEntity::getRoleId).collect(Collectors.toSet());

        List<RoleMenuEntity> roleMenuList = roleMenuService.list(
                new QueryWrapper<RoleMenuEntity>().lambda()
                        .in(RoleMenuEntity::getRoleId, position)
        );
        // 所有菜单的id
        Set<Long> permissions = roleMenuList.stream().map(RoleMenuEntity::getMenuId).collect(Collectors.toSet());

        List<String> menuList = menuService.list(
                new QueryWrapper<MenuEntity>().lambda().in(MenuEntity::getId, permissions)
        ).stream().map(MenuEntity::getCode).collect(Collectors.toList());

        UserData userData = new UserData();
        userData.setId(entity.getId());
        userData.setUsername(entity.getUsername());
        userData.setRealname(entity.getRealname());
        userData.setPosition(position);
        userData.setPermissions(permissions);
        userData.setMenuList(menuList);
        userData.setHeadimg(entity.getHeadimg());
        userData.setEmail(entity.getEmail());
        return userData;
    }
}
