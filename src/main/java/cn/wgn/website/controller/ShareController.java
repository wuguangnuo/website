package cn.wgn.website.controller;

import cn.wgn.website.service.IShareService;
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
public class ShareController extends BaseController {
    @Autowired
    private IShareService shareService;

    @GetMapping(value = "")
    @ApiOperation(value = "获取分享临时链接")
    public void getFileUrl(String name, HttpServletResponse response) throws IOException {
        OutputStream outputStream = response.getOutputStream();
        response.setHeader("content-type", "text/html;charset=UTF-8");
        String data = shareService.sharePage(name);
        outputStream.write(data.getBytes(StandardCharsets.UTF_8));
    }
}
