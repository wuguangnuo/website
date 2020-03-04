package cn.wgn.website.service.impl;

import cn.wgn.website.dto.manage.IpDto;
import cn.wgn.website.dto.manage.Novel;
import cn.wgn.website.dto.manage.NovelDto;
import cn.wgn.website.dto.manage.NovelQueryDto;
import cn.wgn.website.entity.NovelEntity;
import cn.wgn.website.enums.NovelTypeEnum;
import cn.wgn.website.enums.StateEnum;
import cn.wgn.website.mapper.NovelMapper;
import cn.wgn.website.service.IManageService;
import cn.wgn.website.utils.CosClientUtil;
import cn.wgn.website.utils.HttpUtil;
import cn.wgn.website.utils.WebSiteUtil;
import cn.wgn.website.utils.WordUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import org.markdownj.MarkdownProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/18 17:37
 */
@Service("manageService")
public class ManageServiceImpl extends BaseServiceImpl implements IManageService {
    @Autowired
    private NovelMapper novelMapper;
    @Autowired
    private HttpUtil httpUtil;
    @Autowired
    private WordUtil wordUtil;
    @Autowired
    private CosClientUtil cosClientUtil;

    /**
     * 测试
     *
     * @return
     */
    @Override
    public IpDto getIp(String ip) {
//        Map<String, String> param = new HashMap<>();
//        param.put("ip", ip);
//        String result = httpUtil.httpPostMap(param, "http://ip.ws.126.net/ipquery");

        IpDto data = new IpDto();
        data.setIp(ip);
        return data;
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
                    .setUpdateTm(LocalDateTime.now())
                    .setState(StateEnum.NORMAL.getValue());
            novelMapper.updateById(entity);
            return entity.getId();
        } else {
            NovelEntity entity = new NovelEntity();
            BeanUtils.copyProperties(novelDto, entity);
            entity.setNovelAuthor(getUserData().getAccount())
                    .setCreateTm(LocalDateTime.now())
                    .setNovelType(novelTypeEnum.toString())
                    .setState(StateEnum.NORMAL.getValue());
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
            novel.setNovelContent(WebSiteUtil.cutContent(entity.getNovelContent(), 100));
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
            novel.setNovelContent(WebSiteUtil.cutContent(entity.getNovelContent(), 100));
            result.add(novel);
        }

        LOG.info("用户[" + getUserData().getAccount() + "]下载 Novel List Excel，查询条件[" + dto.toString() + "]");
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
        qw.lambda().eq(NovelEntity::getState, StateEnum.NORMAL.getValue());
        if (!Strings.isNullOrEmpty(dto.getOrderBy())) {
            String[] s = dto.getOrderBy().split(" ");
            qw.orderBy(true, "ASC".equalsIgnoreCase(s[1]), s[0]);
        } else {
            qw.lambda().orderByDesc(NovelEntity::getId);
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
        if (entity != null
                && StateEnum.NORMAL.getValue().equals(entity.getState())
                && getUserData().getAccount().equals(entity.getNovelAuthor())) {
            return entity;
        } else {
            return null;
        }
    }

    /**
     * 删除小说(逻辑删除)
     *
     * @param novelId
     * @return
     */
    @Override
    public String novelDelete(Integer novelId) {
        NovelEntity entity = novelMapper.selectById(novelId);
        String data;
        if (entity == null) {
            data = "Novel ID = [" + novelId + "]不存在!";
        } else if (!StateEnum.NORMAL.getValue().equals(entity.getState())) {
            data = "Novel ID = [" + novelId + "]已被删除!";
        } else if (!getUserData().getAccount().equals(entity.getNovelAuthor())) {
            data = "不是作者无权删除!";
        } else {
            entity.setState(StateEnum.DELETE.getValue());
            int res = novelMapper.updateById(entity);
            if (res == 1) {
                data = "1";
            } else {
                data = "操作失败";
            }
        }
        return data;
    }

    /**
     * 下载文档
     *
     * @param id
     * @return
     */
    @Override
    public String downloadDoc(Integer id) {
        NovelEntity novelEntity = novelMapper.selectById(id);
        if (novelEntity == null) {
            return null;
        }
        String htmlBody = novelEntity.getNovelContent();
        if (NovelTypeEnum.Markdown.toString().equals(novelEntity.getNovelType())) {
            MarkdownProcessor markdownProcessor = new MarkdownProcessor();
            htmlBody = markdownProcessor.markdown(htmlBody);
        }

        String filePath = wordUtil.html2Word(htmlBody);
        String url = cosClientUtil.uploadFile2Cos(new File(filePath), "noveldoc");

        LOG.info("用户[" + getUserData().getAccount() + "]下载 Novel，ID=[" + id + "]，URL=[" + url + "]");
        return url;
    }
}
