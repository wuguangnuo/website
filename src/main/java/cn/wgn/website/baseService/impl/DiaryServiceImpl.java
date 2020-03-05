package cn.wgn.website.baseService.impl;

import cn.wgn.website.entity.DiaryEntity;
import cn.wgn.website.mapper.DiaryMapper;
import cn.wgn.website.baseService.IDiaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 諾的日记 服务实现类
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-03-05
 */
@Service
public class DiaryServiceImpl extends ServiceImpl<DiaryMapper, DiaryEntity> implements IDiaryService {

}
