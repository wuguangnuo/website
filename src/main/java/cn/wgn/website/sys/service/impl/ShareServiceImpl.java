package cn.wgn.website.sys.service.impl;

import cn.wgn.framework.utils.HtmlModel;
import cn.wgn.framework.utils.StringUtil;
import cn.wgn.framework.web.service.impl.BaseServiceImpl;
import cn.wgn.website.sys.entity.ShareEntity;
import cn.wgn.website.sys.mapper.ShareMapper;
import cn.wgn.website.sys.service.IShareService;
import cn.wgn.framework.utils.CosClientUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 分享系统表 服务实现类
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-27
 */
@Service
public class ShareServiceImpl extends BaseServiceImpl<ShareMapper, ShareEntity> implements IShareService {
    @Autowired
    private CosClientUtil cosClientUtil;

    /**
     * 分享页面Html
     *
     * @param name 文件名
     * @return
     */
    @Override
    public String sharePage(String name) {
        String data;
        if (Strings.isNullOrEmpty(name)) {
            data = HtmlModel.sharePage("链接错误", "");
            LOG.error("文件分享系统 share: 链接错误");
            return data;
        } else {
            ShareEntity shareEntity = this.getOne(
                    new QueryWrapper<ShareEntity>().lambda()
                            .eq(ShareEntity::getName, name)
                            .orderByDesc(ShareEntity::getId)
                    , false
            );

            // todo 枚举，更多判断
            if (shareEntity != null && !"1".equals(shareEntity.getStatus())) {
                data = HtmlModel.sharePage("文件已取消分享", "");
                LOG.error("文件分享系统 share: 文件已取消分享");
                shareEntity.setRequestNum(shareEntity.getRequestNum() + 1);
                this.updateById(shareEntity);
                return data;
            }

            String key = "share/" + name;
            Map meta = cosClientUtil.getMeta(key);
            if (meta == null) {
                data = HtmlModel.sharePage("文件不存在", "");
                LOG.error("文件分享系统 share: name=[" + name + "] 文件不存在");
                return data;
            } else {
                String url = cosClientUtil.getTmpUrl(key);
                DecimalFormat df = new DecimalFormat("#,###");
                data = HtmlModel.sharePage("获取文件成功",
                        "<h2>文件名称：<a href='" + url + "'>" + name + "</a>&nbsp;<button id='copybtn'data-clipboard-text='" + url + "'>复制链接</button></h2>" +
                                "<h2>文件链接：<a href='" + url + "'style='font-size:.75rem'>" + StringUtil.substring(url, 0, 100) + "...</a></h2>" +
                                "<p>点击链接下载，链接生存时间10分钟</p>" +
                                "<h2>Content-Type：" + meta.get("Content-Type") + "</h2>" +
                                "<h2>Content-Length：" + df.format(meta.get("Content-Length")) + " Byte</h2>" +
                                "<h2>Date：" + new Date() + "</h2>" +
                                "<h2>Last-Modified：" + meta.get("Last-Modified") + "</h2>");
                LOG.info("文件分享系统 share: name=[" + name + "] 获取文件成功");

                this.saveOfUpdate(name, meta);

                return data;
            }
        }
    }

    /**
     * 保存或更新分享系统
     *
     * @param name 文件名
     * @param meta 文件信息
     */
    private void saveOfUpdate(String name, Map meta) {
        ShareEntity shareEntity = this.getOne(
                new QueryWrapper<ShareEntity>().lambda()
                        .eq(ShareEntity::getName, name)
                        .orderByDesc(ShareEntity::getId)
                , false
        );
        if (shareEntity == null) {
            shareEntity = new ShareEntity();
            shareEntity.setName(name);
            shareEntity.setContentType((String) meta.get("Content-Type"));
            shareEntity.setContentLength((Long) meta.get("Content-Length"));
            shareEntity.setLastModified((Date) meta.get("Last-Modified"));
            shareEntity.setStatus("1");
            shareEntity.setSuccessNum(1L);
            shareEntity.setRequestNum(1L);
            this.save(shareEntity);
        } else {
            shareEntity.setContentType((String) meta.get("Content-Type"));
            shareEntity.setContentLength((Long) meta.get("Content-Length"));
            shareEntity.setLastModified((Date) meta.get("Last-Modified"));
            shareEntity.setSuccessNum(shareEntity.getSuccessNum() + 1);
            shareEntity.setRequestNum(shareEntity.getRequestNum() + 1);
            this.updateById(shareEntity);
        }
    }
}
