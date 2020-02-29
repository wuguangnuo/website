package cn.wgn.website.controller;

import cn.wgn.website.dto.ApiRes;
import cn.wgn.website.dto.common.AccountLogin;
import cn.wgn.website.enums.RedisPrefixKeyEnum;
import cn.wgn.website.service.ICommonService;
import cn.wgn.website.utils.CosClientUtil;
import cn.wgn.website.utils.RedisUtil;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

/**
 * 通用控制器，不涉及业务
 *
 * @author WuGuangNuo
 * @date Created in 2020/2/16 13:22
 */
@Api(tags = "通用")
@RestController
public class CommonController extends BaseController {
    @Autowired
    private Producer producer;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CosClientUtil cosClientUtil;

    private final ICommonService commonService;

    public CommonController(ICommonService commonServiceImpl) {
        this.commonService = commonServiceImpl;
    }

    // 验证码过期时间(分钟)
    private static final int expireTime = 15;

    @GetMapping(value = "")
    @ApiOperation(value = "welcome")
    public String welcome() {
        LOG.info("welcome --- LOG.info()");
        return "<a href='/swagger-ui.html'>cn.wgn.website API</a>";
    }

    @PostMapping("login")
    @ApiOperation(value = "登录")
    public ApiRes login(@RequestBody @Valid AccountLogin accountLogin) {
        return commonService.loginByPd(accountLogin);
    }

    @GetMapping("captcha.jpg")
    @ApiOperation("验证码")
    public void captcha(HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String code = UUID.randomUUID().toString().substring(0, 4);

        String key = UUID.randomUUID().toString();
        redisUtil.set(key, RedisPrefixKeyEnum.Captcha.toString(), code, expireTime);
//        request.getSession().setAttribute(RedisPrefixKeyEnum.Captcha.toString(), key);

        BufferedImage bi = producer.createImage(code);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        out.flush();
    }

    @PostMapping(value = "uploadFile")
    @ApiOperation(value = "上传文件到COS")
    public ApiRes<String> uploadFile(@ApiParam(value = "上传文件", required = true) MultipartFile file) {
        if (file == null) {
            return ApiRes.fail("文件不能为空");
        }
        String result = cosClientUtil.uploadFile2COS(file, "temp");
        return ApiRes.suc("上传成功", result);
    }
}
