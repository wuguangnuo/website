package cn.wgn.website.service.impl;

import cn.wgn.website.dto.ApiRes;
import cn.wgn.website.dto.profile.Menu;
import cn.wgn.website.dto.SelectListItem;
import cn.wgn.website.dto.common.AccountLogin;
import cn.wgn.website.dto.common.LoginData;
import cn.wgn.website.dto.profile.MenuTree;
import cn.wgn.website.dto.profile.ProfileDto;
import cn.wgn.website.entity.RolePermissionEntity;
import cn.wgn.website.entity.UserEntity;
import cn.wgn.website.enums.RedisPrefixKeyEnum;
import cn.wgn.website.mapper.RolePermissionMapper;
import cn.wgn.website.mapper.UserMapper;
import cn.wgn.website.service.IProfileService;
import cn.wgn.website.utils.*;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/22 18:54
 */
@Service("profileService")
public class ProfileServiceImpl extends BaseServiceImpl implements IProfileService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private EncryptUtil encryptUtil;
    @Autowired
    private FileUtil fileUtil;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private CosClientUtil cosClientUtil;
    @Autowired
    private ThumbnailsUtil thumbnailsUtil;

    /**
     * 账号密码登录
     *
     * @return
     */
    @Override
    public ApiRes loginByPd(AccountLogin accountLogin) {
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

        String token = UUID.randomUUID().toString().replaceAll("-", "");
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

    /**
     * 获取树形菜单
     *
     * @return
     */
    @Override
    public List<MenuTree> getMenuTree() {
        List<Menu> menus = getMenuList();

        List<MenuTree> result = new LinkedList<>();
        MenuTree menuTree = null;
        SelectListItem item;

        for (Menu m : menus) {
//            if (m.getCode().equals("index")) {
//                continue;
//            }
            if (!m.getUrl().contains("/")) {
                if (menuTree != null) {
                    result.add(menuTree);
                }
                menuTree = new MenuTree();
                menuTree.setIcon(m.getIcon())
                        .setIndex(m.getUrl())
                        .setTitle(m.getName())
                        .setSubs(new LinkedList<>());
            } else {
                item = new SelectListItem();
                item.setText(m.getName())
                        .setValue(m.getUrl().substring(m.getUrl().lastIndexOf("/")));
                assert menuTree != null;
                menuTree.getSubs().add(item);
            }
        }
        result.add(menuTree);
        return result;
    }

    /**
     * 获取个人信息
     *
     * @return
     */
    @Override
    public ProfileDto getProfile() {
        UserEntity entity = userMapper.selectById(getUserData().getId());
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
        String url;
        if (dto.getHeadimg().startsWith("http")) {
            url = dto.getHeadimg();
        } else {
            if (dto.getHeadimg().split(",").length != 2) {
                return "图片需要含URI";
            }
            url = this.uploadHeadimg(dto.getHeadimg());
        }

        dto.setPassword(encryptUtil.encryptString(dto.getPassword()));
        dto.setHeadimg(url);

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

        UserEntity userEntity = userMapper.selectById(getUserData().getId());
        userEntity.setHeadimg(url);
        userMapper.updateById(userEntity);

        return url;
    }

    /**
     * 获取菜单列表
     *
     * @return
     */
    public List<Menu> getMenuList() {
        String jsonStr = fileUtil.getMenuJson();
        List<Menu> menus = JSONArray.parseArray(jsonStr, Menu.class);

        // 获取用户的权限 匹配的code
        List<RolePermissionEntity> powers = rolePermissionMapper.selectList(
                new QueryWrapper<RolePermissionEntity>().lambda()
                        .eq(RolePermissionEntity::getRoleId, getUserData().getRoleId())
        );

        List<Menu> result = new ArrayList<>();
        for (Menu m : menus) {
            for (RolePermissionEntity p : powers) {
                if (m.getCode().equals(p.getPermissionId())) {
                    result.add(m);
                }
            }
        }

        return result;
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
        MultipartFile file = fileUtil.base64ToMultipart(base64);
        String url = cosClientUtil.uploadFile2Cos(file, "headimg");
        return url;
    }
}
