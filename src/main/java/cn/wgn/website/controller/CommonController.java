package cn.wgn.website.controller;

import cn.wgn.website.dto.ApiRes;
import cn.wgn.website.dto.CommonData;
import cn.wgn.website.dto.common.AccountLogin;
import cn.wgn.website.dto.utils.EmailInfo;
import cn.wgn.website.enums.RedisPrefixKeyEnum;
import cn.wgn.website.service.ICommonService;
import cn.wgn.website.utils.*;
import com.google.code.kaptcha.Producer;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
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
    @Autowired
    private ICommonService commonService;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private TencentAIUtil tencentAIUtil;

    // 验证码过期时间(分钟)
    private static final int expireTime = 15;
    // 发邮件时间戳
    private static long timestamp = System.currentTimeMillis();

    @GetMapping(value = "")
    @ApiOperation(value = "welcome")
    public void welcome(HttpServletResponse response) throws IOException {
        LOG.info("welcome --- LOG.info()");
        OutputStream outputStream = response.getOutputStream();
        response.setHeader("content-type", "text/html;charset=UTF-8");
        String data = "<a href='/swagger-ui.html'>cn.wgn.website API</a>";
        outputStream.write(data.getBytes(StandardCharsets.UTF_8));
    }

    @PostMapping("login")
    @ApiOperation(value = "登录")
    public ApiRes login(@RequestBody @Valid AccountLogin accountLogin, HttpServletRequest request) {
        return commonService.loginByPd(accountLogin, request);
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
        String result = cosClientUtil.uploadFile2Cos(file, "temp");
        return ApiRes.suc("上传成功", result);
    }

    @PostMapping(value = "sendMail")
    @ApiOperation(value = "发送邮件")
    public ApiRes<String> sendMail(@RequestBody CommonData data) {
        String eMail = data.getData();
        if (Strings.isNullOrEmpty(eMail)) {
            ApiRes.fail("eMail 不能为空!");
        }
        if (!eMail.matches("[\\w.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
            ApiRes.err("邮箱地址格式错误!");
        }

        long timestamp2 = System.currentTimeMillis() / 1000;
        if (timestamp2 - timestamp < 120) {
            ApiRes.fail("请求过于频繁，请稍后重试！");
        } else {
            timestamp = timestamp2;
        }

        String subject = "测试邮件 from wgn API";
        String content = HtmlModel.mailBody("测试邮件", "<p>测试邮件 from wgn API</p>");
        EmailInfo emailInfo = new EmailInfo(eMail, subject, content);
        boolean b = emailUtil.sendHtmlMail(emailInfo);
        return b ? ApiRes.suc("发送成功!") : ApiRes.err("发送失败");
    }

    @PostMapping(value = "textChat")
    @ApiOperation(value = "智能闲聊")
    public ApiRes<String> textChat(@RequestBody CommonData data, HttpServletRequest request) {
        String question = data.getData();
        if (Strings.isNullOrEmpty(question)) {
            return ApiRes.suc("Success", "说点什么?");
        }
        // 登录用户使用token,否则每小时重置
        String session = request.getHeader("token") == null
                ? System.currentTimeMillis() / 3600000 + ""
                : request.getHeader("token");
        String answer = tencentAIUtil.textChat(session, question);
        if (Strings.isNullOrEmpty(answer)) {
            return ApiRes.suc("Success", "NLP TextChat Error!");
        } else {
            return ApiRes.suc("Success", answer);
        }
    }

    @PostMapping(value = "textTranslate")
    @ApiOperation(value = "中英互译")
    public ApiRes<String> textTranslate(@RequestBody CommonData data) {
        String text = data.getData();
        if (Strings.isNullOrEmpty(text)) {
            return ApiRes.suc("Success", "");
        }
        String trans = tencentAIUtil.textTranslate(text, 0);
        if (Strings.isNullOrEmpty(trans)) {
            return ApiRes.suc("Success", "NLP Translate Error!");
        } else {
            return ApiRes.suc("Success", trans);
        }
    }
}
