package cn.wgn.website.www.controller;

import cn.wgn.framework.web.ApiRes;
import cn.wgn.framework.web.controller.BaseController;
import cn.wgn.framework.web.service.IVisitorService;
import cn.wgn.website.sys.entity.*;
import cn.wgn.website.sys.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * https://www.wuguangnuo.cn
 * 网站主页接口
 * </p>
 *
 * @author WuGuangNuo
 * @date Created in 2020/7/5 17:18
 */
@RequestMapping("home")
@Api(tags = "主页")
@RestController
public class HomeController extends BaseController {
    @Autowired
    private IDiaryService diaryService;
    @Autowired
    private IDemoService demoService;
    @Autowired
    private IGameService gameService;
    @Autowired
    private IDocService docService;
    @Autowired
    private IToolService toolService;
    @Autowired
    private IBlogService blogService;
    @Autowired
    private IVisitorService visitorService;

    @GetMapping(value = "index")
    @ApiOperation(value = "首页")
    public ApiRes index() {
        return ApiRes.suc();
    }

    @GetMapping(value = "diary")
    @ApiOperation(value = "日记")
    public ApiRes<DiaryEntity> diary() {
        // 获取最后一条
        DiaryEntity res = diaryService.getOne(
                new QueryWrapper<DiaryEntity>().lambda()
                        .orderByDesc(DiaryEntity::getCreateTime)
                        .last("LIMIT 1")
        );

        if (res == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(res);
        }
    }

    @GetMapping(value = "demo")
    @ApiOperation(value = "示例")
    public ApiRes<List<DemoEntity>> demo() {
        List<DemoEntity> res = demoService.randList();

        if (res == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(res);
        }
    }

    @GetMapping(value = "game")
    @ApiOperation(value = "游戏")
    public ApiRes<List<GameEntity>> game() {
        List<GameEntity> res = gameService.randList();

        if (res == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(res);
        }
    }

    @GetMapping(value = "doc")
    @ApiOperation(value = "开发文档")
    public ApiRes<List<DocEntity>> doc() {
        List<DocEntity> res = docService.randList();

        if (res == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(res);
        }
    }

    @GetMapping(value = "tool")
    @ApiOperation(value = "工具箱")
    public ApiRes<List<ToolEntity>> tool() {
        List<ToolEntity> res = toolService.randList();

        if (res == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(res);
        }
    }

    @PostMapping(value = "blogList")
    @ApiOperation(value = "博文列表")
    public ApiRes<PageInfo<BlogEntity>> blogList(@RequestBody HashMap<String, String> query) {
        this.startPage();
        List<BlogEntity> res = blogService.queryBlogList(query);

        if (res == null) {
            return ApiRes.fail();
        } else {
            return pageData(res);
        }
    }

    @PostMapping(value = "blogList2")
    @ApiOperation(value = "博文列表2")
    public ApiRes<HashMap<String, String>> blogList2(@RequestBody HashMap<String, String> query) {
        this.startPage();
        HashMap res = blogService.queryBlogList2(query);

        if (res == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(res);
        }
    }

    @GetMapping(value = "blogSide")
    @ApiOperation(value = "相关博客")
    public ApiRes<List<BlogEntity>> blogSide() {
        List<BlogEntity> res = blogService.randList(8);

        if (res == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(res);
        }
    }

    @GetMapping(value = "blogDetail/{id}")
    @ApiOperation(value = "博文详情")
    public ApiRes<BlogEntity> blogDetail(@PathVariable("id") Integer id) {
        if (id == null || id <= 0) {
            return ApiRes.fail();
        }
        BlogEntity res = blogService.getById(id);

        if (res == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(res);
        }
    }

    @PostMapping(value = "vistorChart")
    @ApiOperation(value = "获取访客图表")
    public ApiRes<String> vistorChart(@RequestBody HashMap<String, String> query) {
        String res = visitorService.vistorChart(query);

        if (Strings.isNullOrEmpty(res)) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc("Success", res);
        }
    }

    @PostMapping(value = "vistorTable")
    @ApiOperation(value = "获取访客表格")
    public ApiRes vistorTable(@RequestBody HashMap<String, String> query) {
        return ApiRes.err("未开发");
    }

}
