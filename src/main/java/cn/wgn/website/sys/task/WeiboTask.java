package cn.wgn.website.sys.task;

import cn.wgn.framework.exception.CommonException;
import cn.wgn.website.sys.entity.WeibotopEntity;
import cn.wgn.website.sys.service.IWeibotopService;
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
            Document document = Jsoup.parse(new URL(URL), 10000);
            Elements elements = document.getElementById("pl_top_realtimehot")
                    .getElementsByTag("tr");

            assert elements.size() == 52;
            List<WeibotopEntity> list = new ArrayList<>();
            LocalDateTime createTime = LocalDateTime.now();
            for (int i = 2; i < 52; i++) {
                Element e = elements.get(i);
                String rank = e.getElementsByClass("td-01").get(0).html();
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
}
