package cn.wgn.website.sys.task;

import cn.wgn.framework.exception.CommonException;
import cn.wgn.website.sys.entity.BaidutopEntity;
import cn.wgn.website.sys.service.IBaidutopService;
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
 * 定时任务 —— 百度
 * https://www.baidu.com/
 *
 * @author WuGuangNuo
 * @date Created in 2020/9/6 22:22
 */
@Component("baiduTask")
public class BaiduTask {
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 定时爬取 百度实时热点排行榜
     */
    private static final String BAIDU_TOP_URL = "http://top.baidu.com/buzz?b=1";

    @Autowired
    private IBaidutopService baidutopService;

    /**
     * 定时爬取 百度风云榜 实时热点排行榜
     * http://top.baidu.com/buzz?b=1
     */
    public void botBaiduTopTask() {
        try {
            Document document = Jsoup.parse(new URL(BAIDU_TOP_URL), 10000);
            Elements elements = document.getElementsByClass("list-table")
                    .get(0).getElementsByTag("tbody").get(0).getElementsByTag("tr");

            // 百度风云榜 无自带排名，此处加上排序
            int i = 0;
            LocalDateTime createTime = LocalDateTime.now();
            List<BaidutopEntity> list = new ArrayList<>();
            for (Element e : elements) {
                if (e.getElementsByClass("first").size() > 0 && e.getElementsByClass("list-title").size() > 0) {
                    System.out.println(e.toString());
                    String ranking = e.getElementsByClass("first").get(0).getElementsByTag("span").get(0).html();
                    String title = e.getElementsByClass("list-title").get(0).html();
                    String score = e.getElementsByClass("last").get(0).getElementsByTag("span").get(0).html();
                    String state = null;
                    if (e.getElementsByClass("keyword").get(0).getElementsByClass("icon").size() > 0) {
                        state = e.getElementsByClass("keyword").get(0).getElementsByClass("icon").get(0).attr("class")
                                .replaceAll("icon ", "").replaceAll("icon-", "");
                    }
                    String trend = e.getElementsByClass("last").get(0).getElementsByTag("span").get(0).attr("class")
                            .replaceAll("icon-", "");

                    i++;
                    BaidutopEntity entity = new BaidutopEntity();
                    entity.setRanking(ranking);
                    entity.setTitle(title);
                    entity.setScore(score);
                    entity.setState(state);
                    entity.setTrend(trend);
                    entity.setCreateTime(createTime);
                    list.add(entity);
                }
            }

            // 批量保存
            baidutopService.saveBatch(list);
            log.info("[biliTask.botBiliolTask] 定时任务执行完成");
        } catch (Exception e) {
            log.error("[biliTask.botBiliolTask] 定时任务发生错误");
            log.error(e.getMessage());
            throw new CommonException("[biliTask.botBiliolTask] 定时任务发生错误", e);
        }
    }
}
