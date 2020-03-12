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

/**
 * 文件分享系统
 *
 * @author WuGuangNuo
 * @date Created in 2020/3/12 22:06
 */
@RequestMapping("share")
@Api(tags = "分享")
@RestController
public class ShareController {
    @Autowired
    private CosClientUtil cosClientUtil;
    private String head = "<!DOCTYPE html><html><head><meta http-equiv='Content-Type'content='text/html;charset=utf-8'><meta http-equiv='X-UA-Compatible'content='IE=edge,chrome=1'><meta name='renderer'content='webkit|ie-comp|ie-stand'><meta name='viewport'content='width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no'><title>文件分享系统|諾</title><link rel='icon'type='image/x-icon'href='http://www.wuguangnuo.cn/favicon.ico'/><link rel='shortcut icon'type='image/x-icon'href='http://www.wuguangnuo.cn/favicon.ico'/><link rel='bookmark'type='image/x-icon'href='http://www.wuguangnuo.cn/favicon.ico'/></head><body>";
    private String foot = "</body></html>";

    @GetMapping(value = "")
    @ApiOperation(value = "获取分享临时链接")
    public void getFileUrl(String name, HttpServletResponse response) throws IOException {
        OutputStream outputStream = response.getOutputStream();
        response.setHeader("content-type", "text/html;charset=UTF-8");
        String data = "";

        if (Strings.isNullOrEmpty(name)) {
            data = head + "<h1>链接错误</h1>" + foot;
        } else {
            if (cosClientUtil.isExis("share/" + name)) {
                data = head + "<p>点击下载&nbsp;<a style='font-size:2em'href='" + cosClientUtil.getTempUrl("share/" + name) + "'>" + name + "</a>&nbsp;链接生存时间10分钟</p>" + foot;
            } else {
                data = head + "<h1>文件不存在</h1>" + foot;
            }
        }

        outputStream.write(data.getBytes(StandardCharsets.UTF_8));
    }
}
