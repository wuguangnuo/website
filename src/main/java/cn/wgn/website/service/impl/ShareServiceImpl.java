package cn.wgn.website.service.impl;

import cn.wgn.website.service.IShareService;
import cn.wgn.website.utils.CosClientUtil;
import cn.wgn.website.utils.HtmlModel;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author WuGuangNuo
 * @date Created in 2020/4/13 10:28
 */
@Service("shareService")
public class ShareServiceImpl extends BaseServiceImpl implements IShareService {
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
        } else {
            String key = "share/" + name;
            Map meta = cosClientUtil.getMeta(key);
            if (meta == null) {
                data = HtmlModel.sharePage("文件不存在", "");
                LOG.error("文件分享系统 share: name=[" + name + "] 文件不存在");
            } else {
                String url = cosClientUtil.getTmpUrl(key);
                DecimalFormat df = new DecimalFormat("#,###");
                data = HtmlModel.sharePage("获取文件成功",
                        "<h2>文件名称：<a href='" + url + "'>" + name + "</a>&nbsp;<button id='copybtn'data-clipboard-text='" + url + "'>复制链接</button></h2>" +
                                "<h2>文件链接：<a href='" + url + "'style='font-size:.75rem'>" + url + "</a></h2>" +
                                "<p>点击链接下载，链接生存时间10分钟</p>" +
                                "<h2>Content-Type：" + meta.get("Content-Type") + "</h2>" +
                                "<h2>Content-Length：" + df.format(meta.get("Content-Length")) + " Byte</h2>" +
                                "<h2>Date：" + new Date() + "</h2>" +
                                "<h2>Last-Modified：" + meta.get("Last-Modified") + "</h2>");
                LOG.info("文件分享系统 share: name=[" + name + "] 获取文件成功");
            }
        }
        return data;
    }
}
