package cn.wgn.website.task;

import cn.wgn.website.baseService.IBotZhihuhotService;
import cn.wgn.website.entity.BotZhihuhotEntity;
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
 * 定时爬取 Zhihu 热榜
 * https://www.zhihu.com/billboard
 *
 * @author WuGuangNuo
 * @date Created in 2020/5/10 21:30
 */
@Slf4j
@Component
public class BotZhihuHotTask {
    private final String URL = "https://www.zhihu.com/billboard";

    @Autowired
    private IBotZhihuhotService botZhihuhotService;

    /**
     * 从0分40秒开始，每隔10分钟执行
     */
    @Scheduled(cron = "40 0/10 * * * ?")
    public void botBilirkTask() {
        try {
            Document document = Jsoup.parse(new URL(URL), 10000);
            Elements elements = document.getElementsByClass("HotList-item");

            assert elements.size() == 50;
            List<BotZhihuhotEntity> list = new ArrayList<>();
            LocalDateTime createTm = LocalDateTime.now();
            for (Element e : elements) {
                String rank = e.getElementsByClass("HotList-itemIndex").get(0).html();
                String title = e.getElementsByClass("HotList-itemTitle").get(0).html();
                String score = e.getElementsByClass("HotList-itemMetrics").get(0).html();

                BotZhihuhotEntity entity = new BotZhihuhotEntity();
                entity.setRanking(rank);
                entity.setTitle(title);
                entity.setScore(score);
                entity.setCreateTm(createTm);
                list.add(entity);
            }
            // 批量保存
            botZhihuhotService.saveBatch(list);
            log.info("[BotZhihuHotTask.java] 定时任务执行完成");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[BotZhihuHotTask.java] 定时任务发生错误");
            log.error(e.getMessage());
        }
    }
}