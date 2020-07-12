package cn.wgn.website.service.impl;

import cn.wgn.website.entity.GameEntity;
import cn.wgn.website.mapper.GameMapper;
import cn.wgn.website.service.IGameService;
import cn.wgn.framework.web.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 諾的H5游戏 服务实现类
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-27
 */
@Service
public class GameServiceImpl extends BaseServiceImpl<GameMapper, GameEntity> implements IGameService {

}
