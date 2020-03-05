package cn.wgn.website.baseService.impl;

import cn.wgn.website.entity.BlogEntity;
import cn.wgn.website.mapper.BlogMapper;
import cn.wgn.website.baseService.IBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 諾的博客 服务实现类
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-03-05
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, BlogEntity> implements IBlogService {

}
