package cn.wgn.website.service.impl;

import cn.wgn.website.dto.RequestPage;
import cn.wgn.website.dto.manage.NovelDto;
import cn.wgn.website.entity.NovelEntity;
import cn.wgn.website.enums.NovelTypeEnum;
import cn.wgn.website.mapper.NovelMapper;
import cn.wgn.website.service.IManageService;
import cn.wgn.website.utils.WebSiteUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/18 17:37
 */
@Service
public class ManageServiceImpl extends BaseServiceImpl implements IManageService {
    @Autowired
    private NovelMapper novelMapper;
    @Autowired
    private WebSiteUtil webSiteUtil;

    /**
     * 测试
     *
     * @return
     */
    @Override
    public String test() {
        return getUserData().toString();
    }

    /**
     * 新增小说
     *
     * @param novelDto
     * @param novelTypeEnum
     * @return
     */
    @Override
    public Object addNovel(NovelDto novelDto, NovelTypeEnum novelTypeEnum) {
        if (novelDto.getId() != null && novelDto.getId() > 0) {
            NovelEntity entity = novelMapper.selectById(novelDto.getId());
            if (entity == null
                    || !novelTypeEnum.toString().equals(entity.getNovelType())
                    || !getUserData().getAccount().equals(entity.getNovelAuthor())) {
                return "您是更新[" + novelTypeEnum.toString() + "]小说吗？为什么我找不到呢？";
            }
            entity.setNovelAuthor(getUserData().getAccount())
                    .setNovelTitle(novelDto.getNovelTitle())
                    .setNovelContent(novelDto.getNovelContent())
                    .setNovelType(novelTypeEnum.toString())
                    .setUpdateTm(LocalDateTime.now());
            novelMapper.updateById(entity);
            return entity.getId();
        } else {
            NovelEntity entity = new NovelEntity();
            BeanUtils.copyProperties(novelDto, entity);
            entity.setNovelAuthor(getUserData().getAccount())
                    .setCreateTm(LocalDateTime.now())
                    .setNovelType(novelTypeEnum.toString());
            novelMapper.insert(entity);
            return entity.getId();
        }
    }

    /**
     * 查看小说列表
     *
     * @param requestPage
     * @return
     */
    @Override
    public IPage<NovelEntity> novelList(RequestPage requestPage) {
        Page page = new Page(requestPage.getPageIndex(), requestPage.getPageSize());
        IPage<NovelEntity> novelEntityIPage = novelMapper.selectPage(page,
                new QueryWrapper<NovelEntity>().lambda()
                        .eq(NovelEntity::getNovelAuthor, getUserData().getAccount())
                        .orderByDesc(NovelEntity::getId)
        );

        List<NovelEntity> list = novelEntityIPage.getRecords();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            NovelEntity entity = (NovelEntity) it.next();
            entity.setNovelContent(webSiteUtil.cutContent(entity.getNovelContent()));
        }
        novelEntityIPage.setRecords(list);
        return novelEntityIPage;
    }

    /**
     * 查看小说
     *
     * @param novelId
     * @return
     */
    @Override
    public NovelEntity novelDetail(Integer novelId) {
        NovelEntity entity = novelMapper.selectById(novelId);
        if (entity != null && getUserData().getAccount().equals(entity.getNovelAuthor())) {
            return entity;
        } else {
            return null;
        }
    }
}
