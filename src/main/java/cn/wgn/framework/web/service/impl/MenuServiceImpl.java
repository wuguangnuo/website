package cn.wgn.framework.web.service.impl;

import cn.wgn.framework.exception.CommonException;
import cn.wgn.framework.web.domain.MenuTree;
import cn.wgn.framework.web.entity.MenuEntity;
import cn.wgn.framework.web.entity.RoleMenuEntity;
import cn.wgn.framework.web.mapper.MenuMapper;
import cn.wgn.framework.web.mapper.RoleMenuMapper;
import cn.wgn.framework.web.service.ICacheService;
import cn.wgn.framework.web.service.IMenuService;
import cn.wgn.framework.web.service.IRoleMenuService;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-27
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, MenuEntity> implements IMenuService {
    @Autowired
    private ICacheService cacheService;
    @Autowired
    private IRoleMenuService roleMenuService;

    /**
     * 获取树形菜单
     *
     * @return
     */
    @Override
    public List<MenuTree> getMenuTree() {
        List<MenuEntity> menus = getMenuList();

        List<MenuTree> result = new LinkedList<>();
        MenuTree menuTree = null;
        HashMap<String, String> item;

        for (MenuEntity m : menus) {
            if (!m.getUrl().contains("/")) {
                if (menuTree != null) {
                    result.add(menuTree);
                }
                menuTree = new MenuTree();
                menuTree.setIcon(m.getIcon());
                menuTree.setIndex(m.getUrl());
                menuTree.setTitle(m.getName());
                menuTree.setSubs(new ArrayList<>());
            } else {
                item = new HashMap<>();
                item.put("text", m.getName());
                item.put("value", m.getUrl().substring(m.getUrl().lastIndexOf("/")));
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
    private List<MenuEntity> getMenuList() {
        List<MenuEntity> menus = cacheService.getMenuJson();

        // 获取用户的权限 匹配的code
        Set<Long> powers = getUserData().getPermissions();

        List<MenuEntity> result = new ArrayList<>();
        for (MenuEntity m : menus) {
            if (powers.contains(m.getId())) {
                result.add(m);
            }
        }

        return result;
    }
}
