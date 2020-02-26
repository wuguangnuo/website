package cn.wgn.website.controller;

import cn.wgn.website.dto.ApiRes;
import cn.wgn.website.dto.RequestPage;
import cn.wgn.website.dto.Table;
import cn.wgn.website.dto.manage.Novel;
import cn.wgn.website.dto.manage.NovelDto;
import cn.wgn.website.dto.manage.NovelQueryDto;
import cn.wgn.website.entity.NovelEntity;
import cn.wgn.website.enums.NovelTypeEnum;
import cn.wgn.website.handler.Authorize;
import cn.wgn.website.service.IManageService;
import cn.wgn.website.utils.ExcelUtil;
import cn.wgn.website.utils.WebSiteUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
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
            String excelTitles = "ID,标题,作者,类型,内容,创建时间,更新时间";
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
