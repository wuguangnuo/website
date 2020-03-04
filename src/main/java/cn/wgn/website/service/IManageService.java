package cn.wgn.website.service;

import cn.wgn.website.dto.manage.IpDto;
import cn.wgn.website.dto.manage.Novel;
import cn.wgn.website.dto.manage.NovelDto;
import cn.wgn.website.dto.manage.NovelQueryDto;
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
     * 获取IP信息
     *
     * @return
     */
    IpDto getIp(String ip);

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
    NovelEntity novelDetail(Integer novelId);

    /**
     * 删除小说(逻辑删除)
     *
     * @param novelId
     * @return
     */
    String novelDelete(Integer novelId);
}
