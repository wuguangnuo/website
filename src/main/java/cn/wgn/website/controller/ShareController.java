package cn.wgn.website.controller;

import cn.wgn.website.utils.CosClientUtil;
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
    private String head = "<!DOCTYPE html><html><head>" +
            "<meta http-equiv='Content-Type'content='text/html;charset=utf-8'>" +
            "<meta http-equiv='X-UA-Compatible'content='IE=edge,chrome=1'>" +
            "<meta name='renderer'content='webkit|ie-comp|ie-stand'>" +
            "<meta name='viewport'content='width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no'>" +
            "<title>文件分享系统|諾</title>" +
            "<link rel='icon'type='image/x-icon'href='https://t.wuguangnuo.cn/favicon.ico'/>" +
            "<link rel='shortcut icon'type='image/x-icon'href='https://t.wuguangnuo.cn/favicon.ico'/>" +
            "<link rel='bookmark'type='image/x-icon'href='https://t.wuguangnuo.cn/favicon.ico'/>" +
            "<style type='text/css'>*{font-family:'Courier New','Microsoft YaHei',SimSun;word-break:break-all}" +
            "html{cursor:url('https://wuguangnuo-1257896087.cos.ap-guangzhou.myqcloud.com/public/img/default.cur'),default}" +
            "a{color:blue;text-decoration:none;cursor:url('https://wuguangnuo-1257896087.cos.ap-guangzhou.myqcloud.com/public/img/select.cur'),default}" +
            "footer{text-align:center}</style></head><body>";
    private String foot = "<footer><p>Copyright &copy; 2020 <a href='http://www.wuguangnuo.cn'target='_blank'>wuguangnuo.cn</a> All Rights Reserved | Author by WuGN</p>" +
            "<p style='color:#888'>京ICP备20006588号</p></footer></body>" +
            "<script src='https://cdn.bootcss.com/clipboard.js/2.0.6/clipboard.min.js'></script>" +
            "<script>new ClipboardJS('#copybtn');/*禁用选取,键盘,拖动,右键*/" +
            "document.onselectstart=function(){return false};" +
            "document.body.onkeydown=function(){return false};" +
            "document.body.ondragstart=function(){return false};" +
            "document.body.oncontextmenu=function(){return false};" +
            "</script></html>";

    @GetMapping(value = "")
    @ApiOperation(value = "获取分享临时链接")
    public void getFileUrl(String name, HttpServletResponse response) throws IOException {
        OutputStream outputStream = response.getOutputStream();
        response.setHeader("content-type", "text/html;charset=UTF-8");
        String data;

        if (Strings.isNullOrEmpty(name)) {
            data = head + "<h1>链接错误</h1>" + foot;
        } else {
            String key = "share/" + name;
            Map meta = cosClientUtil.getMeta(key);
            if (meta == null) {
                data = head + "<h1>文件不存在</h1>" + foot;
                LOG.error("文件分享系统 share: name=[" + name + "] 文件不存在");
            } else {
                String url = cosClientUtil.getTmpUrl(key);
                DecimalFormat df = new DecimalFormat("#,###");
                data = head + "<h1>获取文件成功！</h1>" +
                        "<h2>文件名称：<a href='" + url + "'>" + name + "</a>&nbsp;&nbsp;<button id='copybtn'data-clipboard-text='" + url + "'>复制链接</button></h2>" +
                        "<h2>文件链接：<a href='" + url + "'style='font-size:.75rem'>" + url + "</a></h2>" +
                        "<p>点击链接下载，链接生存时间10分钟</p>" +
                        "<h2>Content-Type：" + meta.get("Content-Type") + "</h2>" +
                        "<h2>Content-Length：" + df.format(meta.get("Content-Length")) + " Byte</h2>" +
                        "<h2>Date：" + new Date() + "</h2>" +
                        "<h2>Last-Modified：" + meta.get("Last-Modified") + "</h2>" + foot;
                LOG.info("文件分享系统 share: name=[" + name + "] 获取文件成功");
            }
        }

        outputStream.write(data.getBytes(StandardCharsets.UTF_8));
    }
}
