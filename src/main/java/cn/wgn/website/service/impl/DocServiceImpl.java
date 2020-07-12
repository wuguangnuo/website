package cn.wgn.website.service.impl;

import cn.wgn.website.entity.DocEntity;
import cn.wgn.website.mapper.DocMapper;
import cn.wgn.website.service.IDocService;
import cn.wgn.framework.web.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 开发文档 服务实现类
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-27
 */
@Service
public class DocServiceImpl extends BaseServiceImpl<DocMapper, DocEntity> implements IDocService {

}
