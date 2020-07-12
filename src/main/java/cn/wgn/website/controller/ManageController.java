package cn.wgn.website.controller;

import cn.wgn.framework.aspectj.annotation.Authorize;
import cn.wgn.framework.constant.Constants;
import cn.wgn.framework.web.ApiRes;
import cn.wgn.framework.web.controller.BaseController;
import cn.wgn.framework.web.service.IVisitorService;
import cn.wgn.website.constant.MagicValue;
import cn.wgn.website.dto.HomeInfo;
import cn.wgn.website.dto.NovelDto;
import cn.wgn.website.dto.NovelQueryDto;
import cn.wgn.website.entity.NovelEntity;
import cn.wgn.website.enums.NovelTypeEnum;
import cn.wgn.website.enums.StateEnum;
import cn.wgn.website.service.IBlogService;
import cn.wgn.website.service.INovelService;
import cn.wgn.website.utils.CosClientUtil;
import cn.wgn.website.utils.WebSiteUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
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
    private INovelService novelService;
    @Autowired
    private IBlogService blogService;
    @Autowired
    private CosClientUtil cosClientUtil;

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

        Object result = novelService.addNovel(novelDto, NovelTypeEnum.Html);

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

        Object result = novelService.addNovel(novelDto, NovelTypeEnum.Markdown);

        if (result instanceof Number) {
            return ApiRes.suc((Number) result);
        } else {
            return ApiRes.err((String) result);
        }
    }

    @Authorize("author")
    @PostMapping("novelList")
    @ApiOperation("查看小说列表")
    public ApiRes<PageInfo<NovelEntity>> novelList(HttpServletResponse response, @RequestBody NovelQueryDto dto) {
        // 校验
        String checkOrderBy = WebSiteUtil.checkOrderBy(dto.getOrderBy(), NovelEntity.class);
        if (!"1".equals(checkOrderBy)) {
            return ApiRes.fail(checkOrderBy);
        }

        // 调整
        WebSiteUtil.sortDto(dto);

        // 判断
//        if ("Excel".equalsIgnoreCase(dto.getExport())) {
//            List<Novel> data = manageService.novelListExcel(dto);
//
//            String fileName = "小说列表";
//            String excelName = "Sheet1";
//            String excelTitles = "ID,标题,作者,类型,状态,内容,创建时间,更新时间";
//            try {
//                ExcelUtil.exportExcel(response, fileName, excelName, excelTitles, data);
//            } catch (Exception e) {
//                LOG.error(e.getMessage() + e);
//            }
//
//            return null;
//        } else {
        this.startPage();
        List<NovelEntity> tbody = novelService.novelList(dto);

        if (tbody != null) {
            return pageData(tbody);
        } else {
            return ApiRes.fail();
        }
//        }
    }

    @Authorize("author")
    @PostMapping("novelDetail")
    @ApiOperation("查看小说")
    public ApiRes<NovelDto> novelDetail(@RequestBody HashMap<String, Integer> data) {
        Integer novelId = data.get(MagicValue.ID);
        if (!data.containsKey(MagicValue.ID) || novelId == null || novelId <= 0) {
            return ApiRes.fail("novel id 错误");
        }
        NovelDto result = novelService.novelDetail(novelId);

        if (result == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc(result);
        }
    }

    @Authorize("author")
    @PostMapping("novelDelete")
    @ApiOperation("删除小说")
    public ApiRes<String> novelDelete(@RequestBody HashMap<String, Integer> data) {
        Integer novelId = data.get(MagicValue.ID);
        if (!data.containsKey(MagicValue.ID) || novelId == null || novelId <= 0) {
            return ApiRes.fail("novel id 错误");
        }
        String result = novelService.novelDelete(novelId);

        if (!"1".equals(result)) {
            return ApiRes.fail(result);
        } else {
            return ApiRes.suc("删除成功");
        }
    }

    @Authorize("author")
    @PostMapping(value = "uploadImg")
    @ApiOperation(value = "上传小说图片到COS")
    public ApiRes<String> uploadFile(@ApiParam(value = "上传文件", required = true) MultipartFile file) {
        if (file == null) {
            return ApiRes.fail("文件不能为空");
        }

        String result = cosClientUtil.uploadFile2Cos(file, "novelimg");
        return ApiRes.suc("上传成功", result);
    }

    @Authorize
    @PostMapping(value = "downloadDoc")
    @ApiOperation(value = "下载文档")
    public ApiRes<String> downloadDoc(@RequestBody HashMap<String, Integer> data) {
        Integer novelId = data.get(MagicValue.ID);
        if (!data.containsKey(MagicValue.ID) || novelId == null || novelId <= 0) {
            return ApiRes.fail("novel id 错误");
        }
        String result = novelService.downloadDoc(novelId);

        if (result == null) {
            return ApiRes.fail();
        } else {
            return ApiRes.suc("Success", result);
        }
    }

    @Authorize("superAdmin")
    @GetMapping(value = "sql/{str}")
    @ApiOperation(value = "sql")
    public ApiRes<String> sql(@PathVariable("str") String sql) {
        return ApiRes.suc("Success", blogService.sql(sql));
    }
}
