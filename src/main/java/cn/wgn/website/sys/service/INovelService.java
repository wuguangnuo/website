package cn.wgn.website.sys.service;

import cn.wgn.website.sys.dto.NovelDto;
import cn.wgn.website.sys.dto.NovelQueryDto;
import cn.wgn.website.sys.entity.NovelEntity;
import cn.wgn.framework.web.service.IBaseService;
import cn.wgn.website.sys.enums.NovelTypeEnum;

import java.util.List;

/**
 * <p>
 * 小说表 服务类
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-27
 */
public interface INovelService extends IBaseService<NovelEntity> {
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
    List<NovelEntity> novelList(NovelQueryDto dto);

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
