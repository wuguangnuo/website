package cn.wgn.framework.web.service.impl;

import cn.wgn.framework.web.entity.RoleMenuEntity;
import cn.wgn.framework.web.mapper.RoleMenuMapper;
import cn.wgn.framework.web.service.IRoleMenuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色权限表 服务实现类
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-27
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends BaseServiceImpl<RoleMenuMapper, RoleMenuEntity> implements IRoleMenuService {

}
