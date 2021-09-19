package cn.wgn.website.sys.task;

import cn.wgn.framework.utils.DateUtil;
import cn.wgn.framework.utils.FileUtil;
import cn.wgn.framework.utils.HtmlModel;
import cn.wgn.framework.utils.HttpUtil;
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
import java.util.Calendar;
import java.util.Date;
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
     * 调用Python分析，每交易日早8点，晚8点发邮件
     */
    public void callPython() {
        if (!isWorkDay(DateUtil.getDate())) {
            return;
        }

        // 调用 stock.py ,获取返回数据
        String[] arguments = new String[]{"python", PY_PATH};
        List<String> strList = execScript(arguments);

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
                "指数/基金量化分析邮件",
                HtmlModel.mailBody("指数/基金量化分析", sb.toString())
        );
        boolean emailRes = EmailUtil.sendHtmlMailSeparately(emailInfo);
        log.info("发送邮件：" + (emailRes ? "成功" : "失败") + emailInfo.toString());
    }

    /**
     * 工作日判断(默认当日)
     *
     * @param date date,默认当日
     * @return true:工作日;false:节日或假日
     */
    private boolean isWorkDay(Date date) {
        date = date == null ? DateUtil.getDate() : date;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        if (week == Calendar.SUNDAY) {
            log.info("今天周日，赌场不开门");
            return false;
        } else if (week == Calendar.SATURDAY) {
            log.info("今天周六，赌场不开门");
            return false;
        }

        String d = DateUtil.dateFormat(DateUtil.getDate(), "yyyyMMdd");
        String ds = HttpUtil.httpGetJson("http://tool.bitefu.net/jiari/?d=" + d);
        //0工作日 1 假日 2节日
        if ("1".equals(ds)) {
            log.info("今天是法定假日，赌场不开门");
            return false;
        } else if ("2".equals(ds)) {
            log.info("今天是节假日，赌场不开门");
            return false;
        } else if ("0".equals(ds)) {
            log.info("今天是工作日，赌场终于开门了，许愿红色");
            return true;
        } else {
            log.error("节假日接口返回异常，d=" + d + ", ds=" + ds);
            return false;
        }
    }

    /**
     * 执行脚本命令
     *
     * @param arguments shell命令
     * @return 执行结果
     */
    private List<String> execScript(String[] arguments) {
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
        return strList;
    }
}
