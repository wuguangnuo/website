package cn.wgn.website.service.impl;

import cn.wgn.website.dto.home.BlogListQuery;
import cn.wgn.website.dto.home.DiaryDto;
import cn.wgn.website.entity.*;
import cn.wgn.website.mapper.*;
import cn.wgn.website.service.IHomeService;
import cn.wgn.website.utils.PageHelpper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/16 10:52
 */
@Service
public class HomeServiceImpl implements IHomeService {
    @Autowired
    private PageHelpper pageHelpper;

    @Autowired
    private DiaryMapper diaryMapper;
    @Autowired
    private DemoMapper demoMapper;
    @Autowired
    private GameMapper gameMapper;
    @Autowired
    private DocMapper docMapper;
    @Autowired
    private ToolMapper toolMapper;
    @Autowired
    private BlogMapper blogMapper;

    /**
     * 获取最新一篇日记
     *
     * @return
     */
    @Override
    public DiaryDto getLastDiary() {
        DiaryEntity entity = diaryMapper.getLastDiary();

        if (entity == null) {
            return null;
        } else {
            DiaryDto result = new DiaryDto();
            BeanUtils.copyProperties(entity, result);
            return result;
        }
    }

    /**
     * 获取实例列表
     *
     * @return
     */
    @Override
    public IPage<DemoEntity> getDemo() {
        Page page = pageHelpper.MAX_SIZE;
        return demoMapper.getDemo(page);
    }

    /**
     * 游戏列表
     *
     * @return
     */
    @Override
    public IPage<GameEntity> getGame() {
        Page page = pageHelpper.MAX_SIZE;
        return gameMapper.getGame(page);
    }

    /**
     * 开发文档列表
     *
     * @return
     */
    @Override
    public IPage<DocEntity> getDoc() {
        Page page = pageHelpper.MAX_SIZE;
        return docMapper.getDoc(page);
    }

    /**
     * 工具箱列表
     *
     * @return
     */
    @Override
    public IPage<ToolEntity> getTool() {
        Page page = pageHelpper.MAX_SIZE;
        return toolMapper.getTool(page);
    }

    /**
     * 获取博文列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public IPage<BlogEntity> getBlogList(BlogListQuery query) {
        Page page = new Page(query.getPageIndex(), query.getPageSize());
        return blogMapper.getBlogList(page);
    }

    /**
     * 获取相关博客
     *
     * @return
     */
    @Override
    public IPage<BlogEntity> getBlogSide() {
        Page page = pageHelpper.DEFAULT_SIZE;
        return blogMapper.getBlogSide(page);
    }

    /**
     * 获取博文详情
     *
     * @return
     */
    @Override
    public BlogEntity getBlogDetail(Integer id) {
        return blogMapper.selectById(id);
    }

}
