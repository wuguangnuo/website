package cn.wgn.website.task;

import cn.wgn.website.entity.BiliolEntity;
import cn.wgn.website.service.IBiliolService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 定时任务 —— BiliBili
 * https://www.bilibili.com/
 *
 * @author WuGuangNuo
 * @date Created in 2020/7/19 11:48
 */
@Slf4j
@Component("biliTask")
public class BiliTask {
    /**
     * B站在线列表链接
     */
    private static final String BILI_OL_URL = "https://www.bilibili.com/video/online.html";

    @Autowired
    private IBiliolService biliolService;

    /**
     * 爬取B站在线列表
     */
    public void botBiliolTask() {
        try {
            Document document = Jsoup.parse(new URL(BILI_OL_URL), 10000);
            Elements elements = document.getElementsByClass("online-list")
                    .get(0).getElementsByClass("ebox");

            // Bili Online 无自带排名，此处加上排序
            int i = 0;
            assert elements.size() == 20;
            List<BiliolEntity> list = new ArrayList<>();
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

                i++;
                BiliolEntity entity = new BiliolEntity();
                entity.setRanking(i + "");
                entity.setTitle(title);
                entity.setAuthor(author);
                entity.setUid(uid);
                entity.setLink(link);
                entity.setPlayNum(playNum);
                entity.setDmNum(dmNum);
                entity.setOlNum(olNum);
                entity.setCreateTime(createTm);
                list.add(entity);
            }
            // 批量保存
            biliolService.saveBatch(list);
            log.info("[BotBiliolTask.java] 定时任务执行完成");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[BotBiliolTask.java] 定时任务发生错误");
            log.error(e.getMessage());
        }
    }
}
