package cn.wgn.website.mapper;

import cn.wgn.website.entity.GameEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Game Mapper 接口
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-02-16
 */
@Repository
public interface GameMapper extends BaseMapper<GameEntity> {
    /**
     * 获取游戏列表
     *
     * @param page
     * @return
     */
    IPage<GameEntity> getGame(Page page);
}
