package cn.wgn.website.sys.service;

import cn.wgn.framework.web.service.IBaseService;
import cn.wgn.website.sys.entity.ShareEntity;

/**
 * <p>
 * 分享系统表 服务类
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-27
 */
public interface IShareService extends IBaseService<ShareEntity> {
    /**
     * 分享页面Html
     *
     * @param name 文件名
     * @return
     */
    String sharePage(String name);
}
