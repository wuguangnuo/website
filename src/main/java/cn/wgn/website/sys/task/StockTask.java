package cn.wgn.website.sys.task;

import cn.wgn.framework.utils.FileUtil;
import cn.wgn.framework.utils.HtmlModel;
import cn.wgn.framework.utils.StringUtil;
import cn.wgn.framework.utils.mail.EmailInfo;
import cn.wgn.framework.utils.mail.EmailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 定时任务 —— 聚宽量化选股
 */
@Component("stockTask")
public class StockTask {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final String BASE_PATH = "/www/wwwroot/api/script/python/";
    private static final String PY_PATH = BASE_PATH + "stock.py";
    private static final String MAIL_PATH = BASE_PATH + "stockmail.txt";

    /**
     * 调用Python分析，每日早8点，晚8点发邮件
     */
    public void callPython() {
        // 调用 stock.py ,获取返回数据
        String[] arguments = new String[]{"python", PY_PATH};
        List<String> strList = new ArrayList<>();
        try {
            Process process = Runtime.getRuntime().exec(arguments);

            int re = process.waitFor();
            log.info("执行脚本" + re + (re == 0 ? " 成功" : " 失败"));

            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                strList.add(line);
                log.info(" :: " + line);
            }
            in.close();
        } catch (Exception e) {
            log.info("执行脚本报错");
            e.printStackTrace();
        }

        // 整理返回数据
        StringBuilder sb = new StringBuilder();
        boolean flag = Boolean.FALSE;
        for (String str : strList) {
            if (flag) {
                sb.append("<p>").append(str).append("</p>");
            }
            if (">>>START>>>".equals(str)) {
                flag = true;
            } else if ("<<<END<<<".equals(str)) {
                flag = false;
                sb.delete(sb.length() - 16, sb.length());
            }
        }
        sb.append("<br /><p><strong style='color:red;'>数据仅供参考，股市有风险入市需谨慎！</strong></p>");

        // 获取邮件接收者
        File file = new File(MAIL_PATH);
        if (!file.exists()) {
            log.error("文件不存在：" + MAIL_PATH);
        }
        String email = FileUtil.getFileString(MAIL_PATH, ",");

        // 发送邮件
        log.info("sb :: " + sb.toString());
        EmailInfo emailInfo = new EmailInfo(
                email,
                "指数/基金量化分析",
                HtmlModel.mailBody("指数/基金量化分析", sb.toString())
        );
        log.info("发送邮件：" + emailInfo.toString());
        EmailUtil.sendHtmlMail(emailInfo);
    }
}
