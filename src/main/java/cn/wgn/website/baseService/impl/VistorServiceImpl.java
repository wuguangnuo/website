package cn.wgn.website.baseService.impl;

import cn.wgn.website.entity.VistorEntity;
import cn.wgn.website.mapper.VistorMapper;
import cn.wgn.website.baseService.IVistorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 访客统计 服务实现类
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-03-05
 */
@Service
public class VistorServiceImpl extends ServiceImpl<VistorMapper, VistorEntity> implements IVistorService {

}
