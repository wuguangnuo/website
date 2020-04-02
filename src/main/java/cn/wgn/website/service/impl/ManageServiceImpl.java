package cn.wgn.website.service.impl;

import cn.wgn.website.dto.manage.HomeInfo;
import cn.wgn.website.dto.manage.Novel;
import cn.wgn.website.dto.manage.NovelDto;
import cn.wgn.website.dto.manage.NovelQueryDto;
import cn.wgn.website.dto.utils.IpRegion;
import cn.wgn.website.entity.NovelEntity;
import cn.wgn.website.enums.NovelTypeEnum;
import cn.wgn.website.enums.StateEnum;
import cn.wgn.website.mapper.NovelMapper;
import cn.wgn.website.mapper.UserMapper;
import cn.wgn.website.mapper.CallMapper;
import cn.wgn.website.service.ICacheService;
import cn.wgn.website.service.IManageService;
import cn.wgn.website.utils.*;
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
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IpUtil ipUtil;
    @Autowired
    private CallMapper callMapper;
    @Autowired
    private ICacheService cacheService;

    /**
     * 获取首页信息
     *
     * @return
     */
    @Override
    public HomeInfo getHomeInfo() {
        HomeInfo homeInfo = userMapper.getHomeInfo(getUserData().getId());
        homeInfo.setLastIp(ipUtil.int2ip(Integer.parseInt(homeInfo.getLastIp())));
        IpRegion ipRegion = ipUtil.getIpRegion(homeInfo.getLastIp());
        String lastAdd = ipRegion.getCountry() + ipRegion.getArea() + ipRegion.getProvince() + ipRegion.getCity()
                + (Strings.isNullOrEmpty(ipRegion.getIsp()) ? "" : ("," + ipRegion.getIsp()));
        homeInfo.setLastAdd(lastAdd);
        homeInfo.setWeekChart(cacheService.getHomeChart());
        return homeInfo;
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
                return "您不是作者，不可修改文章！";
            }
            entity.setNovelAuthor(getUserData().getAccount())
                    .setNovelTitle(novelDto.getNovelTitle())
                    .setNovelContent(novelDto.getNovelContent())
                    .setNovelType(novelTypeEnum.toString())
                    .setUpdateTm(LocalDateTime.now())
                    .setState(novelDto.getNovelState());
            novelMapper.updateById(entity);
            return entity.getId();
        } else {
            NovelEntity entity = new NovelEntity();
            BeanUtils.copyProperties(novelDto, entity);
            entity.setNovelAuthor(getUserData().getAccount())
                    .setCreateTm(LocalDateTime.now())
                    .setNovelType(novelTypeEnum.toString())
                    .setState(novelDto.getNovelState());
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
            novel.setNovelState(StateEnum.getLabel(entity.getState()));
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
            novel.setNovelState(StateEnum.getLabel(entity.getState()));
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
        // 作者或公共
        qw.lambda().and(
                q -> q.eq(NovelEntity::getNovelAuthor, getUserData().getAccount())
                        .or()
                        .eq(NovelEntity::getState, StateEnum.PUBLIC.getValue())
        );
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
        // 不展示不存在状态
        qw.lambda().ne(NovelEntity::getState, StateEnum.NOTHING.getValue());
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
    public NovelDto novelDetail(Integer novelId) {
        NovelEntity entity = novelMapper.selectById(novelId);
        if (entity != null
                && (StateEnum.PUBLIC.getValue().equals(entity.getState())
                || getUserData().getAccount().equals(entity.getNovelAuthor()))) {
            NovelDto dto = new NovelDto();
            BeanUtils.copyProperties(entity, dto);
            dto.setNovelState(entity.getState());
            return dto;
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
        } else if (!getUserData().getAccount().equals(entity.getNovelAuthor())) {
            data = "不是作者无权删除!";
        } else {
            // 状态改为已删除，已删除的改为不存在
            if (entity.getState().equals(StateEnum.DELETE.getValue())) {
                entity.setState(StateEnum.NOTHING.getValue());
            } else {
                entity.setState(StateEnum.DELETE.getValue());
            }
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
