package cn.wgn.website.sys.task;

import cn.wgn.framework.exception.CommonException;
import cn.wgn.website.sys.entity.ZhihuhotEntity;
import cn.wgn.website.sys.service.IZhihuhotService;
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
 * 定时任务 ———— 知乎
 *
 * @author WuGuangNuo
 * @date Created in 2020/7/24 21:51
 */
@Slf4j
@Component("zhihuTask")
public class ZhihuTask {
    /**
     * 知乎热榜链接
     */
    private static final String URL = "https://www.zhihu.com/billboard";

    @Autowired
    private IZhihuhotService zhihuhotService;

    /**
     * 定时爬取 Zhihu 热榜
     * https://www.zhihu.com/billboard
     */
    public void botZhihuhotTask() {
        try {
            Document document = Jsoup.parse(new URL(URL), 10000);
            Elements elements = document.getElementsByClass("HotList-item");

            assert elements.size() == 50;
            List<ZhihuhotEntity> list = new ArrayList<>();
            LocalDateTime createTime = LocalDateTime.now();
            for (Element e : elements) {
                String rank = e.getElementsByClass("HotList-itemIndex").get(0).html();
                String title = e.getElementsByClass("HotList-itemTitle").get(0).html();
                String score = e.getElementsByClass("HotList-itemMetrics").get(0).html();

                ZhihuhotEntity entity = new ZhihuhotEntity();
                entity.setRanking(rank);
                entity.setTitle(title);
                entity.setScore(score);
                entity.setCreateTime(createTime);
                list.add(entity);
            }
            // 批量保存
            zhihuhotService.saveBatch(list);
            log.info("[zhihuTask.botZhihuhotTask] 定时任务执行完成");
        } catch (Exception e) {
            log.error("[zhihuTask.botZhihuhotTask] 定时任务发生错误");
            log.error(e.getMessage());
            throw new CommonException("[zhihuTask.botZhihuhotTask] 定时任务发生错误", e);
        }
    }
}
