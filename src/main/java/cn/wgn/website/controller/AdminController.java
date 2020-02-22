package cn.wgn.website.controller;

import cn.wgn.website.dto.ApiRes;
import cn.wgn.website.dto.admin.AddNovel;
import cn.wgn.website.handler.Authorize;
import cn.wgn.website.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 需要管理员权限的控制器
 *
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

    @Authorize("admin") // 需要某个权限
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

    @Authorize("author")
    @PostMapping("addEdit")
    @ApiOperation("新增小说")
    public ApiRes addEdit(@RequestBody AddNovel addNovel) {
        String result = adminService.addEdit(addNovel);

        if ("1".equals(result)) {
            return ApiRes.suc();
        } else {
            return ApiRes.err(result);
        }
    }

    @Authorize("author")
    @PostMapping("addMarkdown")
    @ApiOperation("新增Markdown小说")
    public ApiRes addMarkdown(@RequestBody AddNovel addNovel) {
        String result = adminService.addMarkdown(addNovel);

        if ("1".equals(result)) {
            return ApiRes.suc();
        } else {
            return ApiRes.err(result);
        }
    }
}
