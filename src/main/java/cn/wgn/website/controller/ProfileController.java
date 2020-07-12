package cn.wgn.website.controller;

import cn.wgn.framework.aspectj.annotation.Authorize;
import cn.wgn.framework.web.ApiRes;
import cn.wgn.framework.web.domain.AccountLogin;
import cn.wgn.framework.web.domain.MenuTree;
import cn.wgn.framework.web.domain.ProfileDto;
import cn.wgn.framework.web.entity.UserEntity;
import cn.wgn.framework.web.service.IMenuService;
import cn.wgn.framework.web.service.IUserService;
import cn.wgn.website.constant.MagicValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * @author WuGuangNuo
 * @date Created in 2020/6/27 20:12
 */
@RestController
@Api(tags = "用户")
@RequestMapping("profile")
public class ProfileController {
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IUserService userService;

    @PostMapping("login")
    @ApiOperation(value = "登录")
    public ApiRes login(@RequestBody @Valid AccountLogin accountLogin, HttpServletRequest request) {
        return userService.loginByPd(accountLogin, request);
    }

    @Authorize
    @GetMapping("menuTree")
    @ApiOperation("获取菜单树形列表")
    public ApiRes<List<MenuTree>> menuTree() {
        List<MenuTree> data = menuService.getMenuTree();

        if (data.size() != 0) {
            return ApiRes.suc(data);
        } else {
            return ApiRes.fail();
        }
    }

    @Authorize
    @PostMapping("getProfile")
    @ApiOperation("获取个人信息")
    public ApiRes<ProfileDto> getProfile() {
        ProfileDto result = userService.getProfile();

        if (result == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(result);
        }
    }

    @Authorize
    @PostMapping("updateProfile")
    @ApiOperation("更新个人信息")
    public ApiRes<String> updateProfile(@RequestBody ProfileDto data) {
        String result = userService.updateProfile(data);

        if ("1".equals(result)) {
            return ApiRes.suc();
        } else {
            return ApiRes.err(result);
        }
    }

    @Authorize
    @PostMapping("updateHeadImg")
    @ApiOperation("更新头像")
    public ApiRes<String> updateHeadImg(@RequestBody HashMap<String, String> data) {
        if (data == null || !data.containsKey(MagicValue.HEAD_IMG)) {
            return ApiRes.fail();
        }
        String result = userService.updateHeadImg(data.get(MagicValue.HEAD_IMG));

        if (result == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(result);
        }
    }
}
