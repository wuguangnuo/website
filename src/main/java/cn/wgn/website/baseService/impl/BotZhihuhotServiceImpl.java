package cn.wgn.website.baseService.impl;

import cn.wgn.website.entity.BotZhihuhotEntity;
import cn.wgn.website.mapper.BotZhihuhotMapper;
import cn.wgn.website.baseService.IBotZhihuhotService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 爬虫知乎热榜 服务实现类
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-05-10
 */
@Service("botZhihuhotService")
public class BotZhihuhotServiceImpl extends ServiceImpl<BotZhihuhotMapper, BotZhihuhotEntity> implements IBotZhihuhotService {

}
