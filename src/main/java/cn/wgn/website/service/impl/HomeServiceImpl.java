package cn.wgn.website.service.impl;

import cn.wgn.website.dto.home.*;
import cn.wgn.website.entity.*;
import cn.wgn.website.mapper.*;
import cn.wgn.website.service.ICacheService;
import cn.wgn.website.service.IHomeService;
import cn.wgn.website.utils.WebSiteUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/16 10:52
 */
@Service("homeService")
public class HomeServiceImpl extends BaseServiceImpl implements IHomeService {
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
    @Autowired
    private ICacheService cacheService;

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
        Page page = WebSiteUtil.PAGE_MAX_SIZE;
        return demoMapper.getDemo(page);
    }

    /**
     * 游戏列表
     *
     * @return
     */
    @Override
    public IPage<GameEntity> getGame() {
        Page page = WebSiteUtil.PAGE_MAX_SIZE;
        return gameMapper.getGame(page);
    }

    /**
     * 开发文档列表
     *
     * @return
     */
    @Override
    public IPage<DocEntity> getDoc() {
        Page page = WebSiteUtil.PAGE_MAX_SIZE;
        return docMapper.getDoc(page);
    }

    /**
     * 工具箱列表
     *
     * @return
     */
    @Override
    public IPage<ToolEntity> getTool() {
        Page page = WebSiteUtil.PAGE_MAX_SIZE;
        return toolMapper.getTool(page);
    }

    /**
     * 获取博文列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public IPage getBlogList(BlogListQuery query) {
        Page page = new Page(query.getPageIndex(), query.getPageSize());
        IPage bloePage = blogMapper.selectPage(page, new QueryWrapper<BlogEntity>().lambda()
                .orderByDesc(BlogEntity::getId)
        );

        List list = bloePage.getRecords();
        List<BlogDto> list2 = new ArrayList();
        BlogDto dto;

        Iterator it = list.iterator();
        while (it.hasNext()) {
            BlogEntity entity = (BlogEntity) it.next();
            dto = new BlogDto();
            BeanUtils.copyProperties(entity, dto);
            dto.setPostDate(WebSiteUtil.dateFormat(entity.getPostDate()));
            dto.setPostContent(WebSiteUtil.cutContent(entity.getPostContent()));
            list2.add(dto);
        }
        bloePage.setRecords(list2);
        return bloePage;
    }

    @Override
    public BlogListDto getBlogList2(BlogListQuery query) {
        IPage data = this.getBlogList(query);

        BlogListDto result = new BlogListDto();
        result.setBlogList(data.getRecords());

        // 本页，最后一页
        long c = data.getCurrent();
        long e = (data.getTotal() / data.getSize()) + (data.getTotal() % data.getSize() == 0 ? 0 : 1);

        StringBuilder sb = new StringBuilder();
        sb.append("<div>");
        sb.append("<a class='first btn" + (c == 1 ? " disabled'" : "' to='?pageIndex=1'") + ">首页</a>");
        sb.append("<a class='prev btn" + (c == 1 ? " disabled'" : "' to='?pageIndex=" + (c - 1) + "'") + ">上一页</a>");
        sb.append(c > 4 ? "<span class='pageEllipsis'>...</span>" : "");

        long t = (c < 4) ? (4 - c) : (c > e - 4 ? e - c - 3 : 0); // 对冲参数
        for (int i = -3; i <= 3; i++) {
            if (i + t == 0) {
                sb.append("<a class='current'>" + c + "</a>");
            } else {
                sb.append("<a class='num' to='?pageIndex=" + (c + i + t) + "'>" + (c + i + t) + "</a>");
            }
        }

        sb.append(c < (e - 3) ? "<span class='pageEllipsis'>...</span>" : "");
        sb.append("<a class='prev btn" + (c == e ? " disabled'" : "' to='?pageIndex=" + (c + 1) + "'") + ">下一页</a>");
        sb.append("<a class='end btn" + (c == e ? " disabled'" : "' to='?pageIndex=" + e + "'") + ">尾页</a>");
        sb.append("<label class='pageTurn'><input to='?pageIndex=%5BPAGE%5D' title='输入页码，按回车快速跳转' value='" + c + "'></input><span title='共 " + e + " 页'> / " + e + " 页</span></label>");
//        </div>
        sb.append("</div>");
        result.setPageInfo(sb.toString());

        return result;
    }

    /**
     * 获取相关博客
     *
     * @return
     */
    @Override
    public List<BlogSideDto> getBlogSide() {
        List<BlogEntity> list = blogMapper.getBlogSide(8);

        List<BlogSideDto> result = new ArrayList<>();
        BlogSideDto dto;
        for (BlogEntity entity : list) {
            dto = new BlogSideDto();
            dto.setId(entity.getId())
                    .setPostTitle(entity.getPostTitle())
                    .setPostTitleCut(WebSiteUtil.cutStr(entity.getPostTitle(), 17));
            result.add(dto);
        }
        return result;
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

    /**
     * 获取访客图表
     *
     * @param dto
     * @return
     */
    @Override
    public String vistorChart(VistorChartDto dto) {
        // 默认最近七天
        LocalDate date1 = dto.getDate1() == null ? LocalDate.now().minusDays(6) : dto.getDate1();
        LocalDate date2 = dto.getDate2() == null ? LocalDate.now() : dto.getDate2();
        // 时间排序
        if (date1.isAfter(date2)) {
            LocalDate tmpDate = date1;
            date1 = date2;
            date2 = tmpDate;
        }
        LocalDateTime dateTime1 = LocalDateTime.of(date1, LocalTime.MIN);
        LocalDateTime dateTime2 = LocalDateTime.of(date2, LocalTime.MAX);

        String result;
        switch (dto.getType()) {
            case "vistor":
                result = cacheService.getVistorChart(LocalDateTime.of(LocalDate.now().minusMonths(3), LocalTime.MIN), dateTime2);
                break;
            case "link":
                result = cacheService.getLinkChart(dateTime1, dateTime2);
                break;
            case "system":
                result = cacheService.getSystemChart(dateTime1, dateTime2);
                break;
            case "browser":
                result = cacheService.getBrowserChart(dateTime1, dateTime2);
                break;
            default:
                result = "";
                break;
        }

        return result;
    }
}
