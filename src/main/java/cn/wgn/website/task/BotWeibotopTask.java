package cn.wgn.website.task;

import cn.wgn.website.baseService.IBotWeibotopService;
import cn.wgn.website.entity.BotWeibotopEntity;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 定时爬取 weibo 热搜榜
 * https://s.weibo.com/top/summary
 *
 * @author WuGuangNuo
 * @date Created in 2020/5/10 21:30
 */
@Slf4j
@Component
public class BotWeibotopTask {
    private final String URL = "https://s.weibo.com/top/summary";

    @Autowired
    private IBotWeibotopService botWeibotopService;

    /**
     * 从0分20秒开始，每隔10分钟执行
     */
    @Scheduled(cron = "20 0/10 * * * ?")
    public void botWeibotopTask() {
        try {
            Document document = Jsoup.parse(new URL(URL), 10000);
            Elements elements = document.getElementById("pl_top_realtimehot")
                    .getElementsByTag("tr");

            assert elements.size() == 52;
            List<BotWeibotopEntity> list = new ArrayList<>();
            LocalDateTime createTm = LocalDateTime.now();
            for (int i = 2; i < 52; i++) {
                Element e = elements.get(i);
                String rank = e.getElementsByClass("td-01").get(0).html();
                String title = e.getElementsByClass("td-02").get(0).getElementsByTag("a").html();
                String score = e.getElementsByClass("td-02").get(0).getElementsByTag("span").html();
                Elements states = e.getElementsByClass("td-03").get(0).getElementsByTag("i");
                String state = states.size() == 0 ? "" : states.get(0).html();

                BotWeibotopEntity entity = new BotWeibotopEntity();
                entity.setRanking(rank);
                entity.setTitle(title);
                entity.setScore(score);
                entity.setState(state);
                entity.setCreateTm(createTm);
                list.add(entity);
            }
            // 批量保存
            botWeibotopService.saveBatch(list);
            log.info("[BotWeibotopTask.java] 定时任务执行完成");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[BotWeibotopTask.java] 定时任务发生错误");
            log.error(e.getMessage());
        }
    }
}