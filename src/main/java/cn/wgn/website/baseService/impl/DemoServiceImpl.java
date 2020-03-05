package cn.wgn.website.baseService.impl;

import cn.wgn.website.entity.DemoEntity;
import cn.wgn.website.mapper.DemoMapper;
import cn.wgn.website.baseService.IDemoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 諾的DEMO 服务实现类
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-03-05
 */
@Service
public class DemoServiceImpl extends ServiceImpl<DemoMapper, DemoEntity> implements IDemoService {

}
