package cn.wgn.website.sys.task;

import cn.wgn.framework.exception.CommonException;
import cn.wgn.website.sys.entity.BiliolEntity;
import cn.wgn.website.sys.entity.BilirkEntity;
import cn.wgn.website.sys.service.IBiliolService;
import cn.wgn.website.sys.service.IBilirkService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 定时任务 —— 哔哩哔哩
 * https://www.bilibili.com/
 *
 * @author WuGuangNuo
 * @date Created in 2020/7/19 11:48
 */
@Component("biliTask")
public class BiliTask {
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * B站在线列表链接
     */
    private static final String BILI_OL_URL = "https://www.bilibili.com/video/online.html";
    /**
     * 定时爬取 bilibili 排行榜
     * （默认：全站榜，全部分类，全部投稿，三日排行）
     */
    private static final String BILI_RK_URL = "https://www.bilibili.com/v/popular/rank/all";


    @Autowired
    private IBiliolService biliolService;
    @Autowired
    private IBilirkService bilirkService;

    /**
     * 定时爬取 bilibili 在线列表
     * https://www.bilibili.com/video/online.html
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
            LocalDateTime createTime = LocalDateTime.now();
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
                entity.setCreateTime(createTime);
                list.add(entity);
            }
            // 批量保存
            biliolService.saveBatch(list);
            log.info("[biliTask.botBiliolTask] 定时任务执行完成");
        } catch (Exception e) {
            log.error("[biliTask.botBiliolTask] 定时任务发生错误");
            log.error(e.getMessage());
            throw new CommonException("[biliTask.botBiliolTask] 定时任务发生错误", e);
        }
    }

    /**
     * 定时爬取 bilibili 排行榜（默认：全站榜，全部分类，全部投稿，三日排行）
     * https://www.bilibili.com/ranking（https://www.bilibili.com/ranking/all/0/0/3）
     */
    public void botBilirkTask() {
        try {
            Document document = Jsoup.parse(new URL(BILI_RK_URL), 10000);
            Elements elements = document.getElementsByClass("rank-list")
                    .get(0).getElementsByClass("rank-item");

            assert elements.size() == 100;
            List<BilirkEntity> list = new ArrayList<>();
            LocalDateTime createTime = LocalDateTime.now();
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

                BilirkEntity entity = new BilirkEntity();
                entity.setRanking(ranking);
                entity.setTitle(title);
                entity.setAuthor(author);
                entity.setUid(uid);
                entity.setLink(link);
                entity.setPlayNum(playNum);
                entity.setDmNum(dmNum);
                entity.setScore(score);
                entity.setCreateTime(createTime);
                list.add(entity);
            }
            // 批量保存
            bilirkService.saveBatch(list);
            log.info("[biliTask.botBilirkTask] 定时任务执行完成");
        } catch (Exception e) {
            log.error("[biliTask.botBilirkTask] 定时任务发生错误");
            log.error(e.getMessage());
            throw new CommonException("[biliTask.botBilirkTask] 定时任务发生错误", e);
        }
    }
}
