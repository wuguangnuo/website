package cn.wgn.website.controller;

import cn.wgn.website.dto.ApiRes;
import cn.wgn.website.dto.CommonData;
import cn.wgn.website.dto.profile.MenuTree;
import cn.wgn.website.entity.UserEntity;
import cn.wgn.website.service.IProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设计人员的接口
 *
 * @author WuGuangNuo
 * @date Created in 2020/2/21 21:08
 */
@RestController
@Api(tags = "用户")
@RequestMapping("profile")
public class ProfileController extends BaseController {
    @Autowired
    private IProfileService profileService;

    @GetMapping("menuTree")
    @ApiOperation("获取菜单树形列表")
    public ApiRes<List<MenuTree>> menuTree() {
        List<MenuTree> data = profileService.getMenuTree();

        if (data.size() != 0) {
            return ApiRes.suc(data);
        } else {
            return ApiRes.fail();
        }
    }

    @PostMapping("getProfile")
    @ApiOperation("获取个人信息")
    public ApiRes<UserEntity> getProfile() {
        UserEntity result = profileService.getProfile();

        if (result == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(result);
        }
    }

    @PostMapping("updateProfile")
    @ApiOperation("更新个人信息")
    public ApiRes<String> updateProfile(@RequestBody UserEntity data) {
        String result = profileService.updateProfile(data);

        if ("1".equals(result)) {
            return ApiRes.suc();
        } else {
            return ApiRes.err(result);
        }
    }

    @PostMapping("updateHeadImg")
    @ApiOperation("更新头像")
    public ApiRes<UserEntity> updateHeadImg(@RequestBody CommonData data) {
        if (data == null || data.getData() == null) {
            return ApiRes.fail();
        }
        String result = profileService.updateHeadImg(data.getData());

        if (result == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(result);
        }
    }
}
