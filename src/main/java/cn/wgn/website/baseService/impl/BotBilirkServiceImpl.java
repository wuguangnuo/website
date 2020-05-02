package cn.wgn.website.baseService.impl;

import cn.wgn.website.entity.BotBilirkEntity;
import cn.wgn.website.mapper.BotBilirkMapper;
import cn.wgn.website.baseService.IBotBilirkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 爬虫_bili排行榜 服务实现类
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-05-02
 */
@Service("botBilirkService")
public class BotBilirkServiceImpl extends ServiceImpl<BotBilirkMapper, BotBilirkEntity> implements IBotBilirkService {

}
