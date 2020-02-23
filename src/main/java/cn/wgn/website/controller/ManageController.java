package cn.wgn.website.controller;

import cn.wgn.website.dto.ApiRes;
import cn.wgn.website.dto.RequestPage;
import cn.wgn.website.dto.manage.NovelDto;
import cn.wgn.website.entity.NovelEntity;
import cn.wgn.website.enums.NovelTypeEnum;
import cn.wgn.website.handler.Authorize;
import cn.wgn.website.service.IManageService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("manage")
public class ManageController extends BaseController {
    private final IManageService manageService;

    public ManageController(IManageService manageServiceImpl) {
        this.manageService = manageServiceImpl;
    }

    @Authorize("admin") // 需要某个权限
    @PostMapping("test")
    @ApiOperation("测试Admin")
    public ApiRes<String> test() {
        String result = manageService.test();

        if (result == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(result);
        }
    }

    @Authorize("author")
    @PostMapping("addEdit")
    @ApiOperation("新增小说")
    public ApiRes addEdit(@RequestBody NovelDto novelDto) {
        Object result = manageService.addNovel(novelDto, NovelTypeEnum.Html);

        if (result instanceof Number) {
            return ApiRes.suc((Number) result);
        } else {
            return ApiRes.err((String) result);
        }
    }

    @Authorize("author")
    @PostMapping("addMarkdown")
    @ApiOperation("新增Markdown小说")
    public ApiRes addMarkdown(@RequestBody NovelDto novelDto) {
        Object result = manageService.addNovel(novelDto, NovelTypeEnum.Markdown);

        if (result instanceof Number) {
            return ApiRes.suc((Number) result);
        } else {
            return ApiRes.err((String) result);
        }
    }

    @Authorize("author")
    @PostMapping("novelList")
    @ApiOperation("查看小说列表")
    public ApiRes<IPage<NovelEntity>> novelList(@RequestBody RequestPage requestPage) {
        IPage<NovelEntity> result = manageService.novelList(requestPage);

        if (result == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(result);
        }
    }

    @Authorize("author")
    @PostMapping("novelDetail")
    @ApiOperation("查看小说")
    public ApiRes<NovelEntity> novelDetail(Integer novelId) {
        if (novelId == null || novelId <= 0) {
            return ApiRes.fail("novel id 错误");
        }
        NovelEntity result = manageService.novelDetail(novelId);

        if (result == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(result);
        }
    }
}
