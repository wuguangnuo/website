package cn.wgn.website.service;

import cn.wgn.website.dto.manage.*;
import cn.wgn.website.entity.NovelEntity;
import cn.wgn.website.enums.NovelTypeEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/18 17:35
 */
public interface IManageService extends IBaseService {
    /**
     * 获取首页信息
     *
     * @return
     */
    HomeInfo getHomeInfo();

    /**
     * 新增小说
     *
     * @param novelDto
     * @param novelTypeEnum
     * @return
     */
    Object addNovel(NovelDto novelDto, NovelTypeEnum novelTypeEnum);

    /**
     * 查看小说列表
     *
     * @param dto
     * @return
     */
    IPage<Novel> novelList(NovelQueryDto dto);

    List<Novel> novelListExcel(NovelQueryDto dto);

    /**
     * 查看小说
     *
     * @param novelId
     * @return
     */
    NovelDto novelDetail(Integer novelId);

    /**
     * 删除小说(逻辑删除)
     *
     * @param novelId
     * @return
     */
    String novelDelete(Integer novelId);

    /**
     * 下载文档
     *
     * @param id
     * @return
     */
    String downloadDoc(Integer id);
}
