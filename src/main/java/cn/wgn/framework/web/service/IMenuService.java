package cn.wgn.framework.web.service;

import cn.wgn.framework.web.domain.MenuTree;
import cn.wgn.framework.web.entity.MenuEntity;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-27
 */
public interface IMenuService extends IBaseService<MenuEntity> {

    /**
     * 获取树形菜单
     *
     * @return
     */
    List<MenuTree> getMenuTree();
}
