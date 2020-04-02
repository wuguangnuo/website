package cn.wgn.website.controller;

import cn.wgn.website.dto.ApiRes;
import cn.wgn.website.dto.CommonData;
import cn.wgn.website.dto.manage.*;
import cn.wgn.website.entity.NovelEntity;
import cn.wgn.website.enums.NovelTypeEnum;
import cn.wgn.website.enums.StateEnum;
import cn.wgn.website.handler.Authorize;
import cn.wgn.website.service.IManageService;
import cn.wgn.website.utils.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.List;

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
    @Autowired
    private IpUtil ipUtil;
    @Autowired
    private IManageService manageService;
    @Autowired
    private CosClientUtil cosClientUtil;

    @Authorize("index")
    @PostMapping("getHomeInfo")
    @ApiOperation("获取首页信息")
    public ApiRes<HomeInfo> getHomeInfo() {
        HomeInfo result = manageService.getHomeInfo();

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
        String state = novelDto.getNovelState();
        if (Strings.isNullOrEmpty(state)) {
            novelDto.setNovelState(StateEnum.PRIVATE.getValue());
        } else if (!StateEnum.PRIVATE.getValue().equals(state) &&
                !StateEnum.PUBLIC.getValue().equals(state)) {
            return ApiRes.err("Novel State Error");
        }

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
        String state = novelDto.getNovelState();
        if (Strings.isNullOrEmpty(state)) {
            novelDto.setNovelState(StateEnum.PRIVATE.getValue());
        } else if (!StateEnum.PRIVATE.getValue().equals(state) &&
                !StateEnum.PUBLIC.getValue().equals(state)) {
            return ApiRes.err("Novel State Error");
        }

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
    public ApiRes<IPage<Novel>> novelList(HttpServletResponse response, @RequestBody NovelQueryDto dto) {
        // 校验
        String checkOrderBy = WebSiteUtil.checkOrderBy(dto.getOrderBy(), NovelEntity.class);
        if (!"1".equals(checkOrderBy)) {
            return ApiRes.fail(checkOrderBy);
        }

        // 调整
        WebSiteUtil.sortDto(dto);

        // 判断
        if ("Excel".equalsIgnoreCase(dto.getExport())) {
            List<Novel> data = manageService.novelListExcel(dto);

            String fileName = "小说列表";
            String excelName = "Sheet1";
            String excelTitles = "ID,标题,作者,类型,状态,内容,创建时间,更新时间";
            try {
                ExcelUtil.exportExcel(response, fileName, excelName, excelTitles, data);
            } catch (Exception e) {
                LOG.error(e.getMessage() + e);
            }

            return null;
        } else {
            IPage<Novel> tbody = manageService.novelList(dto);

            if (tbody != null) {
                return ApiRes.suc(tbody);
            } else {
                return ApiRes.fail();
            }
        }
    }

    @Authorize("author")
    @PostMapping("novelDetail")
    @ApiOperation("查看小说")
    public ApiRes<NovelDto> novelDetail(@RequestBody CommonData data) {
        Integer novelId = data.getId();
        if (novelId == null || novelId <= 0) {
            return ApiRes.fail("novel id 错误");
        }
        NovelDto result = manageService.novelDetail(novelId);

        if (result == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(result);
        }
    }

    @Authorize("author")
    @PostMapping("novelDelete")
    @ApiOperation("删除小说")
    public ApiRes<String> novelDelete(@RequestBody CommonData data) {
        Integer novelId = data.getId();
        if (novelId == null || novelId <= 0) {
            return ApiRes.fail("novel id 错误");
        }
        String result = manageService.novelDelete(novelId);

        if (!"1".equals(result)) {
            return ApiRes.fail(result);
        } else {
            return ApiRes.suc("删除成功");
        }
    }

    @PostMapping(value = "uploadImg")
    @ApiOperation(value = "上传图片到COS")
    public ApiRes<String> uploadFile(@ApiParam(value = "上传文件", required = true) MultipartFile file) {
        if (file == null) {
            return ApiRes.fail("文件不能为空");
        }

        String result = cosClientUtil.uploadFile2Cos(file, "novelimg");
        return ApiRes.suc("上传成功", result);
    }

    @PostMapping(value = "downloadDoc")
    @ApiOperation(value = "下载文档")
    public ApiRes<String> downloadDoc(@RequestBody CommonData data) {
        if (data.getId() == null) {
            return ApiRes.fail();
        }
        String result = manageService.downloadDoc(data.getId());

        if (result == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc("Success", result);
        }
    }
}
