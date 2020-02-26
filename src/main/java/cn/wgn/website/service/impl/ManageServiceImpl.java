package cn.wgn.website.service.impl;

import cn.wgn.website.dto.RequestPage;
import cn.wgn.website.dto.manage.Novel;
import cn.wgn.website.dto.manage.NovelDto;
import cn.wgn.website.dto.manage.NovelQueryDto;
import cn.wgn.website.entity.NovelEntity;
import cn.wgn.website.enums.NovelTypeEnum;
import cn.wgn.website.mapper.NovelMapper;
import cn.wgn.website.service.IManageService;
import cn.wgn.website.utils.WebSiteUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
     * @param dto
     * @return
     */
    @Override
//    @Cacheable(value = "novelList", key = "#dto.toString()+#dto.pageIndex+'_'+#dto.pageSize")
    public Page novelList(NovelQueryDto dto) {
        Page page = new Page(dto.getPageIndex(), dto.getPageSize());
        Page novelPage = novelMapper.selectPage(page, novelListQw(dto));

        List<NovelEntity> records = novelPage.getRecords();
        List<Novel> result = new ArrayList<>();
        Novel novel;
        for (NovelEntity entity : records) {
            novel = new Novel();
            BeanUtils.copyProperties(entity, novel);
            novel.setNovelContent(WebSiteUtil.cutContent(entity.getNovelContent(), 20));
            result.add(novel);
        }
        novelPage.setRecords(result);
        return novelPage;
    }

    /**
     * 查看小说列表（Excel）
     *
     * @param dto
     * @return
     */
    @Override
//    @Cacheable(value = "novelListExcel", key = "#dto.toString()+#dto.pageIndex+'_'+#dto.pageSize")
    public List<Novel> novelListExcel(NovelQueryDto dto) {
        List<NovelEntity> list = novelMapper.selectList(novelListQw(dto));
        List<Novel> result = new ArrayList<>();
        Novel novel;
        for (NovelEntity entity : list) {
            novel = new Novel();
            BeanUtils.copyProperties(entity, novel);
            novel.setNovelContent(WebSiteUtil.cutContent(entity.getNovelContent(), 20));
            result.add(novel);
        }
        return result;
    }

    /**
     * 查看小说列表（QueryWrapper）
     *
     * @param dto
     * @return
     */
    private QueryWrapper<NovelEntity> novelListQw(NovelQueryDto dto) {
        QueryWrapper<NovelEntity> qw = new QueryWrapper<NovelEntity>();
        if (getUserData().getAccount() != null) {
            qw.lambda().like(NovelEntity::getNovelAuthor, getUserData().getAccount());
        }
        if (!Strings.isNullOrEmpty(dto.getNovelTitle())) {
            qw.lambda().like(NovelEntity::getNovelTitle, dto.getNovelTitle());
        }
        if (!Strings.isNullOrEmpty(dto.getNovelType())) {
            qw.lambda().eq(NovelEntity::getNovelType, dto.getNovelType());
        }
        if (dto.getCreateTm1() != null) {
            qw.lambda().gt(NovelEntity::getCreateTm, dto.getCreateTm1());
        }
        if (dto.getCreateTm2() != null) {
            qw.lambda().lt(NovelEntity::getCreateTm, dto.getCreateTm2());
        }
        if (dto.getUpdateTm1() != null) {
            qw.lambda().gt(NovelEntity::getCreateTm, dto.getUpdateTm1());
        }
        if (dto.getUpdateTm2() != null) {
            qw.lambda().lt(NovelEntity::getCreateTm, dto.getUpdateTm2());
        }
        if (!Strings.isNullOrEmpty(dto.getOrderBy())) {
            String[] s = dto.getOrderBy().split(" ");
            qw.orderBy(true, "ASC".equalsIgnoreCase(s[1]), s[0]);
        }
        return qw;
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
