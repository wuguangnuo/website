package cn.wgn.website.service;

import cn.wgn.website.dto.admin.AddNovel;

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
    String addEdit(AddNovel addNovel);


    /**
     * 新增Markdown小说
     *
     * @param addNovel
     * @return
     */
    String addMarkdown(AddNovel addNovel);
}
