package cn.wgn.website.controller;

import cn.wgn.website.dto.ApiRes;
import cn.wgn.website.dto.home.BlogListDto;
import cn.wgn.website.dto.home.BlogListQuery;
import cn.wgn.website.dto.home.BlogSideDto;
import cn.wgn.website.dto.home.DiaryDto;
import cn.wgn.website.entity.*;
import cn.wgn.website.service.IHomeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 主页接口(不需要权限)
 *
 * @author WuGuangNuo
 * @date Created in 2020/2/16 10:50
 */
@RequestMapping("home")
@Api(tags = "主页")
@RestController
public class HomeController extends BaseController {
    @Autowired
    private IHomeService homeService;

    @GetMapping(value = "index")
    @ApiOperation(value = "首页")
    public ApiRes index() {
        return ApiRes.suc();
    }

    @PostMapping(value = "diary")
    @ApiOperation(value = "日记")
    public ApiRes<DiaryDto> diary() {
        DiaryDto res = homeService.getLastDiary();

        if (res == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(res);
        }
    }

    @PostMapping(value = "demo")
    @ApiOperation(value = "示例")
    public ApiRes<IPage<DemoEntity>> demo() {
        IPage<DemoEntity> res = homeService.getDemo();

        if (res == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(res);
        }
    }

    @PostMapping(value = "game")
    @ApiOperation(value = "游戏")
    public ApiRes<IPage<GameEntity>> game() {
        IPage<GameEntity> res = homeService.getGame();

        if (res == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(res);
        }
    }

    @PostMapping(value = "doc")
    @ApiOperation(value = "开发文档")
    public ApiRes<IPage<DocEntity>> doc() {
        IPage<DocEntity> res = homeService.getDoc();

        if (res == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(res);
        }
    }

    @PostMapping(value = "tool")
    @ApiOperation(value = "工具箱")
    public ApiRes<IPage<ToolEntity>> tool() {
        IPage<ToolEntity> res = homeService.getTool();

        if (res == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(res);
        }
    }

    @PostMapping(value = "blogList")
    @ApiOperation(value = "博文列表")
    public ApiRes<IPage> blogList(@RequestBody BlogListQuery query) {
        IPage res = homeService.getBlogList(query);

        if (res == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(res);
        }
    }

    @PostMapping(value = "blogList2")
    @ApiOperation(value = "博文列表2")
    public ApiRes<BlogListDto> blogList2(@RequestBody BlogListQuery query) {
        BlogListDto res = homeService.getBlogList2(query);

        if (res == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(res);
        }
    }

    @PostMapping(value = "blogSide")
    @ApiOperation(value = "相关博客")
    public ApiRes<List<BlogSideDto>> blogList() {
        List<BlogSideDto> res = homeService.getBlogSide();

        if (res == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(res);
        }
    }

    @PostMapping(value = "blogDetail")
    @ApiOperation(value = "博文详情")
    public ApiRes<BlogEntity> blogList(Integer id) {
        if (id == null || id <= 0) {
            return ApiRes.fail();
        }
        BlogEntity res = homeService.getBlogDetail(id);

        if (res == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(res);
        }
    }
}
