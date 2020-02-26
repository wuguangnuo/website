package cn.wgn.website.service.impl;

import cn.wgn.website.dto.ApiRes;
import cn.wgn.website.dto.Menu;
import cn.wgn.website.dto.SelectListItem;
import cn.wgn.website.dto.common.AccountLogin;
import cn.wgn.website.dto.common.LoginData;
import cn.wgn.website.dto.profile.MenuTree;
import cn.wgn.website.entity.UserEntity;
import cn.wgn.website.enums.RedisPrefixKeyEnum;
import cn.wgn.website.mapper.UserMapper;
import cn.wgn.website.service.IProfileService;
import cn.wgn.website.utils.EncryptUtil;
import cn.wgn.website.utils.RedisUtil;
import cn.wgn.website.utils.WebSiteUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/22 18:54
 */
@Service
public class ProfileServiceImpl extends BaseServiceImpl implements IProfileService {
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
        if (!userEntity.getPassword().equals(encryptUtil.getMD5Str(accountLogin.getPassword()))) {
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
                        .setIndex(m.getCode())
                        .setTitle(m.getName())
                        .setSubs(new LinkedList<>());
            } else {
                item = new SelectListItem();
                item.setText(m.getName())
                        .setValue(m.getCode());
                assert menuTree != null;
                menuTree.getSubs().add(item);
            }
        }
        result.add(menuTree);
        return result;
    }

    /**
     * 获取菜单列表
     *
     * @return
     */
    private List<Menu> getMenuList() {
        // 获取用户的权限 匹配的code

        String jsonStr = "[\n" +
                "    {\n" +
                "        \"code\":\"index\",\n" +
                "        \"icon\":\"el-icon-lx-home\",\n" +
                "        \"url\":\"index\",\n" +
                "        \"name\":\"系统首页\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"code\":\"write\",\n" +
                "        \"icon\":\"el-icon-edit\",\n" +
                "        \"url\":\"write\",\n" +
                "        \"name\":\"写作\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"code\":\"novellist\",\n" +
                "        \"icon\":\"el-icon-lx-cascades\",\n" +
                "        \"url\":\"write/novellist\",\n" +
                "        \"name\":\"小说列表\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"code\":\"editor\",\n" +
                "        \"icon\":\"el-icon-lx-home\",\n" +
                "        \"url\":\"write/editor\",\n" +
                "        \"name\":\"富文本编辑器\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"code\":\"markdown\",\n" +
                "        \"icon\":\"el-icon-lx-home\",\n" +
                "        \"url\":\"write/markdown\",\n" +
                "        \"name\":\"markdown编辑器\"\n" +
                "    }" +
                "]";

        List<Menu> menus = JSONArray.parseArray(jsonStr, Menu.class);
        return menus;
    }
}
