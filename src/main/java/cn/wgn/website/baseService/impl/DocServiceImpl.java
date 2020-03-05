package cn.wgn.website.baseService.impl;

import cn.wgn.website.entity.DocEntity;
import cn.wgn.website.mapper.DocMapper;
import cn.wgn.website.baseService.IDocService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 开发文档 服务实现类
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-03-05
 */
@Service
public class DocServiceImpl extends ServiceImpl<DocMapper, DocEntity> implements IDocService {

}
