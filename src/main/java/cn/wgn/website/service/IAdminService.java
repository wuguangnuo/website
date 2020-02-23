package cn.wgn.website.service;

import cn.wgn.website.dto.RequestPage;
import cn.wgn.website.dto.admin.AddNovel;
import cn.wgn.website.entity.NovelEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/18 17:35
 */
public interface IAdminService {
    /**
     * 测试
     *
     * @return
     */
    String test();

    /**
     * 新增小说
     *
     * @param addNovel
     * @return
     */
    Object addEdit(AddNovel addNovel);


    /**
     * 新增Markdown小说
     *
     * @param addNovel
     * @return
     */
    Object addMarkdown(AddNovel addNovel);

    /**
     * 查看小说列表
     *
     * @param requestPage
     * @return
     */
    IPage<NovelEntity> novelList(RequestPage requestPage);

    /**
     * 查看小说
     *
     * @param novelId
     * @return
     */
    NovelEntity novelDetail(Integer novelId);
}
