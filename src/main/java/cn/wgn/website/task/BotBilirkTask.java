package cn.wgn.website.task;

import cn.wgn.website.baseService.IBotBilirkService;
import cn.wgn.website.entity.BotBilirkEntity;
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
 * 定时爬取 bilibili 排行榜（默认：全站榜，全部分类，全部投稿，三日排行）
 * https://www.bilibili.com/ranking（https://www.bilibili.com/ranking/all/0/0/3）
 *
 * @author WuGuangNuo
 * @date Created in 2020/5/2 21:30
 */
@Slf4j
@Component
public class BotBilirkTask {
    private final String URL = "https://www.bilibili.com/ranking";

    @Autowired
    private IBotBilirkService botBilirkService;

    /**
     * 每日 04:31 定时任务
     * （Bili Rank榜单每日4:30更新）
     */
    @Scheduled(cron = "0 31 4 1/1 * ?")
    public void botBilirkTask() {
        try {
            Document document = Jsoup.parse(new URL(URL), 10000);
            Elements elements = document.getElementsByClass("rank-list")
                    .get(0).getElementsByClass("rank-item");

            assert elements.size() == 100;
            List<BotBilirkEntity> list = new ArrayList<>();
            LocalDateTime createTm = LocalDateTime.now();
            for (Element e : elements) {
                String ranking = e.getElementsByClass("num").html();
                String title = e.getElementsByTag("a").get(1).html();
                String author = e.getElementsByClass("data-box").get(2).childNode(1).toString();
                String uid = e.getElementsByTag("a").get(2).attr("href");
                uid = uid.replaceAll("space.bilibili.com", "").replaceAll("/", "");
                String link = e.getElementsByTag("a").get(1).attr("href");
                link = link.replaceAll("www.bilibili.com/video", "").replaceAll("/", "").replaceAll("https:", "");
                String playNum = e.getElementsByClass("data-box").get(0).childNode(1).toString();
                String dmNum = e.getElementsByClass("data-box").get(1).childNode(1).toString();
                String score = e.getElementsByClass("pts").get(0).childNode(0).childNode(0).toString();

                BotBilirkEntity entity = new BotBilirkEntity();
                entity.setRanking(ranking);
                entity.setTitle(title);
                entity.setAuthor(author);
                entity.setUid(uid);
                entity.setLink(link);
                entity.setPlayNum(playNum);
                entity.setDmNum(dmNum);
                entity.setScore(score);
                entity.setCreateTm(createTm);
                list.add(entity);
            }
            // 批量保存
            botBilirkService.saveBatch(list);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[BotBilirkTask.java] 定时任务发生错误");
            log.error(e.getMessage());
        }
    }
}