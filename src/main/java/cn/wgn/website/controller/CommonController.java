package cn.wgn.website.controller;

import cn.wgn.website.dto.ApiRes;
import cn.wgn.website.dto.common.AccountLogin;
import cn.wgn.website.service.ICommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/16 13:22
 */
@Api(tags = "通用")
@RestController
public class CommonController extends BaseController {
    private final ICommonService commonService;

    public CommonController(ICommonService commonServiceImpl) {
        this.commonService = commonServiceImpl;
    }

    @GetMapping(value = "")
    @ApiOperation(value = "welcome")
    private String welcome() {
        LOG.info("welcome --- LOG.info()");
        return "<a href='/swagger-ui.html'>cn.wgn.website API</a>";
    }

    @PostMapping("account/login")
    @ApiOperation(value = "登录")
    public ApiRes login(@RequestBody @Valid AccountLogin accountLogin) {
        return commonService.loginByPd(accountLogin);
    }
}
