package cn.wgn.website.api.controller;

import cn.wgn.website.sys.service.IShareService;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author WuGuangNuo
 * @date Created in 2020/6/27 13:52
 */
@Api(tags = "分享")
@RestController
@RequestMapping("share")
public class ShareController {
    @Autowired
    private IShareService shareService;

    /**
     * 获取分享临时链接
     * the url like "http://127.0.0.1:8800/share/1.png"
     *
     * @param name
     * @param response
     * @throws IOException
     */
    @GetMapping("{name}")
    @ApiOperation(value = "获取分享临时链接")
    public void share(@PathVariable("name") String name, HttpServletResponse response) throws IOException {
        OutputStream outputStream = response.getOutputStream();
        response.setHeader("content-type", "text/html;charset=UTF-8");
        String data = shareService.sharePage(name);
        outputStream.write(data.getBytes(Constants.UTF_8));
    }

    /**
     * 获取分享临时链接
     * the url like "http://127.0.0.1:8800/share?name=1.png"
     *
     * @param name
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "")
    @ApiOperation(value = "获取分享临时链接")
    public void getFileUrl(String name, HttpServletResponse response) throws IOException {
        OutputStream outputStream = response.getOutputStream();
        response.setHeader("content-type", "text/html;charset=UTF-8");
        String data = shareService.sharePage(name);
        outputStream.write(data.getBytes(Constants.UTF_8));
    }
}
