package cn.wgn.website.sys.controller;

import cn.wgn.framework.aspectj.annotation.Authorize;
import cn.wgn.framework.web.ApiRes;
import cn.wgn.framework.web.controller.BaseController;
import cn.wgn.framework.web.service.IVisitorService;
import cn.wgn.website.sys.dto.HomeInfo;
import cn.wgn.website.sys.service.IBlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 管理控制层
 *
 * @author WuGuangNuo
 * @date Created in 2020/7/5 17:18
 */
@RestController
@Api(tags = "管理")
@RequestMapping("manage")
public class ManageController extends BaseController {
    @Autowired
    private IVisitorService visitorService;
    @Autowired
    private IBlogService blogService;

    @Authorize("index")
    @PostMapping("getHomeInfo")
    @ApiOperation("获取首页信息")
    public ApiRes<HomeInfo> getHomeInfo() {
        HomeInfo result = visitorService.getHomeInfo();

        if (result == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(result);
        }
    }

    @Authorize("superAdmin")
    @GetMapping(value = "sql/{str}")
    @ApiOperation(value = "sql")
    public ApiRes<String> sql(@PathVariable("str") String sql) {
        return ApiRes.suc("Success", blogService.sql(sql));
    }
}
