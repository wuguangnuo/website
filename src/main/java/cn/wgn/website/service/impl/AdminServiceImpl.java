package cn.wgn.website.service.impl;

import cn.wgn.website.dto.admin.AddNovel;
import cn.wgn.website.entity.NovelEntity;
import cn.wgn.website.enums.NovelTypeEnum;
import cn.wgn.website.mapper.NovelMapper;
import cn.wgn.website.service.IAdminService;
import cn.wgn.website.service.IBaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/18 17:37
 */
@Service
public class AdminServiceImpl implements IAdminService, IBaseService {
    @Autowired
    private NovelMapper novelMapper;

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
     * @param addNovel
     * @return
     */
    @Override
    public String addEdit(AddNovel addNovel) {
        if (addNovel.getId() != null) {
            NovelEntity entity = novelMapper.selectById(addNovel.getId());
            if (entity == null
                    && NovelTypeEnum.Html.toString().equals(entity.getNovelType())
                    && getUserData().getAccount().equals(entity.getNovelAuthor())) {
                return "您是更新[Html]小说吗？为什么我找不到呢？";
            }
            entity.setNovelAuthor(getUserData().getAccount())
                    .setNovelTitle(addNovel.getNovelTitle())
                    .setNovelContent(addNovel.getNovelContent())
                    .setNovelType(NovelTypeEnum.Html.toString())
                    .setUpdateTm(LocalDateTime.now());
            novelMapper.updateById(entity);
            return "1";
        } else {
            NovelEntity entity = new NovelEntity();
            BeanUtils.copyProperties(addNovel, entity);
            entity.setNovelAuthor(getUserData().getAccount())
                    .setCreateTm(LocalDateTime.now())
                    .setNovelType(NovelTypeEnum.Html.toString());
            novelMapper.insert(entity);
            return "1";
        }
    }

    /**
     * 新增Markdown小说
     *
     * @param addNovel
     * @return
     */
    @Override
    public String addMarkdown(AddNovel addNovel) {
        if (addNovel.getId() != null) {
            NovelEntity entity = novelMapper.selectById(addNovel.getId());
            if (entity == null
                    && NovelTypeEnum.Markdowm.toString().equals(entity.getNovelType())
                    && getUserData().getAccount().equals(entity.getNovelAuthor())) {
                return "您是更新[Markdown]小说吗？为什么我找不到呢？";
            }
            entity.setNovelAuthor(getUserData().getAccount())
                    .setNovelTitle(addNovel.getNovelTitle())
                    .setNovelContent(addNovel.getNovelContent())
                    .setNovelType(NovelTypeEnum.Markdowm.toString())
                    .setUpdateTm(LocalDateTime.now());
            novelMapper.updateById(entity);
            return "1";
        } else {
            NovelEntity entity = new NovelEntity();
            BeanUtils.copyProperties(addNovel, entity);
            entity.setNovelAuthor(getUserData().getAccount())
                    .setCreateTm(LocalDateTime.now())
                    .setNovelType(NovelTypeEnum.Markdowm.toString());
            novelMapper.insert(entity);
            return "1";
        }
    }
}
