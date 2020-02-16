package cn.wgn.website.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/16 13:22
 */
@Api(tags = "通用")
@RestController
public class CommonController extends BaseController {

    @GetMapping(value = "")
    @ApiOperation(value = "welcome")
    private String welcome() {
        LOG.info("welcome --- LOG.info()");
        return "<a href='/swagger-ui.html'>cn.wgn.website API</a>";
    }
}
