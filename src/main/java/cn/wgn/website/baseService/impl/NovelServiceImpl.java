package cn.wgn.website.baseService.impl;

import cn.wgn.website.entity.NovelEntity;
import cn.wgn.website.mapper.NovelMapper;
import cn.wgn.website.baseService.INovelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 小说表 服务实现类
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-03-05
 */
@Service
public class NovelServiceImpl extends ServiceImpl<NovelMapper, NovelEntity> implements INovelService {

}
