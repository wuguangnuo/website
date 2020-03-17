package cn.wgn.website.controller;

import cn.wgn.website.utils.CosClientUtil;
import cn.wgn.website.utils.HtmlModel;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;

/**
 * 文件分享系统
 *
 * @author WuGuangNuo
 * @date Created in 2020/3/12 22:06
 */
@RequestMapping("share")
@Api(tags = "分享")
@RestController
public class ShareController extends BaseController {
    @Autowired
    private CosClientUtil cosClientUtil;

    @GetMapping(value = "")
    @ApiOperation(value = "获取分享临时链接")
    public void getFileUrl(String name, HttpServletResponse response) throws IOException {
        OutputStream outputStream = response.getOutputStream();
        response.setHeader("content-type", "text/html;charset=UTF-8");
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

        outputStream.write(data.getBytes(StandardCharsets.UTF_8));
    }
}
