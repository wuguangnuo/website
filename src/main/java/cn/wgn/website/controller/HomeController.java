package cn.wgn.website.controller;

import cn.wgn.website.dto.ApiRes;
import cn.wgn.website.dto.home.DiaryDto;
import cn.wgn.website.entity.DemoEntity;
import cn.wgn.website.entity.DocEntity;
import cn.wgn.website.entity.GameEntity;
import cn.wgn.website.entity.ToolEntity;
import cn.wgn.website.service.IHomeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/16 10:50
 */
@RequestMapping("home")
@Api(tags = "主页")
@RestController
public class HomeController extends BaseController {
    private final IHomeService homeService;

    public HomeController(IHomeService homeServiceImpl) {
        this.homeService = homeServiceImpl;
    }

    @GetMapping(value = "index")
    @ApiOperation(value = "首页")
    public ApiRes index() {
        return ApiRes.suc();
    }

    @PostMapping(value = "diary")
    @ApiOperation(value = "日记")
    public ApiRes diary() {
        DiaryDto res = homeService.getLastDiary();

        if (res == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(res);
        }
    }

    @PostMapping(value = "demo")
    @ApiOperation(value = "示例")
    public ApiRes demo() {
        IPage<DemoEntity> res = homeService.getDemo();

        if (res == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(res);
        }
    }

    @PostMapping(value = "game")
    @ApiOperation(value = "游戏")
    public ApiRes game() {
        IPage<GameEntity> res = homeService.getGame();

        if (res == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(res);
        }
    }

    @PostMapping(value = "doc")
    @ApiOperation(value = "开发文档")
    public ApiRes doc() {
        IPage<DocEntity> res = homeService.getDoc();

        if (res == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(res);
        }
    }

    @PostMapping(value = "tool")
    @ApiOperation(value = "工具箱")
    public ApiRes tool() {
        IPage<ToolEntity> res = homeService.getTool();

        if (res == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(res);
        }
    }
}
