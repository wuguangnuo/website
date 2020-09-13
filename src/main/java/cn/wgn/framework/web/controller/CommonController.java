package cn.wgn.framework.web.controller;

import cn.wgn.framework.constant.CharsetKit;
import cn.wgn.framework.utils.HtmlModel;
import cn.wgn.framework.web.ApiRes;
import cn.wgn.framework.web.domain.AccountLogin;
import cn.wgn.framework.web.service.ICommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author WuGuangNuo
 * @date Created in 2020/5/29 23:34
 */
@Api(tags = "通用")
@RestController
public class CommonController {
    public Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    private ICommonService commonService;

    @GetMapping(value = "")
    @ApiOperation(value = "welcome")
    public void welcome(HttpServletResponse response) throws IOException {
        LOG.info("welcome --- LOG.info()");
        OutputStream outputStream = response.getOutputStream();
        response.setHeader("content-type", "text/html;charset=UTF-8");
        String data = HtmlModel.welcomePage();
        outputStream.write(data.getBytes(CharsetKit.CHARSET_UTF_8));
    }

    @GetMapping("urule/*")
    @ApiOperation("URULE已关闭")
    public void urule(HttpServletResponse response) throws IOException {
        OutputStream outputStream = response.getOutputStream();
        response.setHeader("content-type", "text/html;charset=UTF-8");
        String data = HtmlModel.closeUrule();
        outputStream.write(data.getBytes(CharsetKit.CHARSET_UTF_8));
    }
}
