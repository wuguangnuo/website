package cn.wgn.website.sys.task;

import cn.wgn.framework.exception.CommonException;
import cn.wgn.framework.utils.HttpUtil;
import cn.wgn.website.sys.entity.WeibotopEntity;
import cn.wgn.website.sys.service.IWeibotopService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时任务 ———— 微博
 *
 * @author WuGuangNuo
 * @date Created in 2020/7/24 21:51
 */
@Component("weiboTask")
public class WeiboTask {
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 微博热搜链接
     */
    private static final String URL = "https://s.weibo.com/top/summary";

    @Autowired
    private IWeibotopService weibotopService;

    /**
     * 定时爬取 weibo 热搜榜
     * https://s.weibo.com/top/summary
     */
    public void botWeibotopTask() {
        try {
//            Document document = Jsoup.parse(new URL(URL), 10000);

            Map<String, String> cookiesMap = getWeiboCookieMap(5);
            Document document = Jsoup.connect(URL).cookies(cookiesMap).get();

            Elements elements = document.getElementById("pl_top_realtimehot")
                    .getElementsByTag("tr");

//            assert elements.size() == 52;
            List<WeibotopEntity> list = new ArrayList<>();
            LocalDateTime createTime = LocalDateTime.now();
            for (int i = 2; i < elements.size(); i++) {
                Element e = elements.get(i);
                String rank = e.getElementsByClass("td-01").get(0).html();

                try {
                    Integer.valueOf(rank);
                } catch (NumberFormatException nfe) {
                    // 广告Item
                    continue;
                }

                String title = e.getElementsByClass("td-02").get(0).getElementsByTag("a").html();
                String score = e.getElementsByClass("td-02").get(0).getElementsByTag("span").html();
                Elements states = e.getElementsByClass("td-03").get(0).getElementsByTag("i");
                String state = states.size() == 0 ? "" : states.get(0).html();

                WeibotopEntity entity = new WeibotopEntity();
                entity.setRanking(rank);
                entity.setTitle(title);
                entity.setScore(score);
                entity.setState(state);
                entity.setCreateTime(createTime);
                list.add(entity);
            }
            // 批量保存
            weibotopService.saveBatch(list);
            log.info("[weiboTask.botWeibotopTask] 定时任务执行完成");
        } catch (Exception e) {
            log.error("[weiboTask.botWeibotopTask] 定时任务发生错误");
            log.error(e.getMessage());
            throw new CommonException("[weiboTask.botWeibotopTask] 定时任务发生错误", e);
        }
    }

    /**
     * 获取Weibo Cookie
     *
     * @param x 重试次数
     * @return
     */
    public Map<String, String> getWeiboCookieMap(int x) {
        if (x <= 0) {
            return null;
        }
        String passport1 = "https://passport.weibo.com/visitor/genvisitor";

        HashMap<String, String> map1 = new HashMap<>();
        map1.put("cb", "gen_callback");
        map1.put("fp", "{'os':'1','browser':'Chrome70','fonts':'undefined','screenInfo':'1920*1080*24','plugins':'baiduplayer Browser Plugin'}");
        String backStr1 = HttpUtil.httpPostMap(map1, passport1);
        log.info("backStr1=" + backStr1);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String dataStr1 = StringUtils.substringBetween(backStr1, "gen_callback(", ");");
        JSONObject jsonData = (JSONObject) JSON.parseObject(dataStr1).get("data");
        String tid = (String) jsonData.get("tid");
        Integer confidence = (Integer) jsonData.get("confidence");
        Boolean new_tid = (Boolean) jsonData.get("new_tid");

        StringBuilder passport2 = new StringBuilder("https://passport.weibo.com/visitor/visitor");
        passport2.append("?a=incarnate&t=").append(tid)
                .append("&w=").append(new_tid ? "3" : "2")
                .append("&c=").append(confidence == null ? "100" : confidence)
                .append("&cb=cross_domain&from=weibo");
        log.info("passport2=" + passport2);
        String backStr2 = HttpUtil.httpGetJson(passport2.toString());
        log.info("backStr2=" + backStr2);

        String dataStr2 = StringUtils.substringBetween(backStr2, "cross_domain(", ");");
        JSONObject jsonData2 = (JSONObject) JSON.parseObject(dataStr2).get("data");
        String sub = (String) jsonData2.get("sub");
        String subp = (String) jsonData2.get("subp");

        if (StringUtils.isEmpty(sub) || StringUtils.isEmpty(subp)) {
            log.info("重试 x=" + x);
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getWeiboCookieMap(x - 1);
        }

        HashMap<String, String> cookiesMap = new HashMap<>();
        cookiesMap.put("SUB", sub);
        cookiesMap.put("SUBP", subp);
        log.info("cookiesMap=" + cookiesMap.toString());

        return cookiesMap;
    }
}
