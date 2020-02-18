package cn.wgn.website.controller;

import cn.wgn.website.dto.ApiRes;
import cn.wgn.website.handler.Authorize;
import cn.wgn.website.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/18 17:30
 */

@RestController
@Api(tags = "管理")
@RequestMapping("admin")
public class AdminController extends BaseController {
    private final IAdminService adminService;

    public AdminController(IAdminService adminServiceImpl) {
        this.adminService = adminServiceImpl;
    }

    @Authorize("admintest") // 需要某个权限
    @PostMapping("test")
    @ApiOperation("测试Admin")
    public ApiRes<String> test() {
        String result = adminService.test();

        if (result == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(result);
        }
    }
}
