package cn.wgn.website.baseService.impl;

import cn.wgn.website.entity.BotWeibotopEntity;
import cn.wgn.website.mapper.BotWeibotopMapper;
import cn.wgn.website.baseService.IBotWeibotopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 爬虫微博热搜 服务实现类
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-05-10
 */
@Service("botWeibotopService")
public class BotWeibotopServiceImpl extends ServiceImpl<BotWeibotopMapper, BotWeibotopEntity> implements IBotWeibotopService {

}
