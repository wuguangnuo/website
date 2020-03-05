package cn.wgn.website.baseService.impl;

import cn.wgn.website.entity.GameEntity;
import cn.wgn.website.mapper.GameMapper;
import cn.wgn.website.baseService.IGameService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 諾的H5游戏 服务实现类
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-03-05
 */
@Service
public class GameServiceImpl extends ServiceImpl<GameMapper, GameEntity> implements IGameService {

}
