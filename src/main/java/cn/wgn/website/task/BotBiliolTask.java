package cn.wgn.website.task;

import cn.wgn.website.baseService.IBotBiliolService;
import cn.wgn.website.entity.BotBiliolEntity;
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
 * 定时爬取 bilibili 在线列表
 * https://www.bilibili.com/video/online.html
 *
 * @author WuGuangNuo
 * @date Created in 2020/5/2 21:30
 */
@Slf4j
@Component
public class BotBiliolTask {
    private final String URL = "https://www.bilibili.com/video/online.html";

    @Autowired
    private IBotBiliolService botBiliolService;

    /**
     * 从0分钟开始，每隔10分钟执行
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void botBiliolTask() {
        try {
            Document document = Jsoup.parse(new URL(URL), 10000);
            Elements elements = document.getElementsByClass("online-list")
                    .get(0).getElementsByClass("ebox");

            // Bili Online 无自带排名，此处加上排序
            int i = 0;
            assert elements.size() == 20;
            List<BotBiliolEntity> list = new ArrayList<>();
            LocalDateTime createTm = LocalDateTime.now();
            for (Element e : elements) {
                String title = e.getElementsByClass("etitle").get(0).html();
                String author = e.getElementsByClass("author").get(0).html();
                String uid = e.getElementsByTag("a").get(1).attr("href");
                uid = uid.replaceAll("space.bilibili.com", "").replaceAll("/", "");
                String link = e.getElementsByTag("a").get(0).attr("href");
                link = link.replaceAll("www.bilibili.com/video", "").replaceAll("/", "");
                String playNum = e.getElementsByClass("play").get(0).childNode(1).toString();
                String dmNum = e.getElementsByClass("dm").get(0).childNode(1).toString();
                String olNum = e.getElementsByClass("ol").get(0).child(0).html();

                BotBiliolEntity entity = new BotBiliolEntity();
                entity.setRanking(i + "");
                entity.setTitle(title);
                entity.setAuthor(author);
                entity.setUid(uid);
                entity.setLink(link);
                entity.setPlayNum(playNum);
                entity.setDmNum(dmNum);
                entity.setOlNum(olNum);
                entity.setCreateTm(createTm);
                list.add(entity);
                i++;
            }
            // 批量保存
            botBiliolService.saveBatch(list);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[BotBiliolTask.java] 定时任务发生错误");
            log.error(e.getMessage());
        }
    }
}