package cn.wgn.website.sys.task;

import cn.wgn.framework.exception.CommonException;
import cn.wgn.framework.utils.DateUtil;
import cn.wgn.framework.utils.HttpUtil;
import cn.wgn.website.sys.entity.AcfunrkEntity;
import cn.wgn.website.sys.service.IAcfunrkService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 定时任务 —— AcFun
 * https://www.bilibili.com/
 *
 * @author WuGuangNuo
 * @date Created in 2020/7/19 11:48
 */
@Component("acfunTask")
public class AcfunTask {
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * AcFun 排行榜最大支持 1000 条查询
     * 默认30条
     */
    private static int DEFAULT_NUM = 30;

    /**
     * 定时爬取 AcFun 排行榜
     * （默认：全站综合，全部，今日）
     */
    private static String ACFUN_RK_URL(Integer num) {
        return "https://www.acfun.cn/rest/pc-direct/rank/channel?channelId=&subChannelId=&rankLimit=" + ((num != null && num > 0) ? num : DEFAULT_NUM) + "&rankPeriod=DAY";
    }

    @Autowired
    private IAcfunrkService acfunrkService;

    /**
     * 定时爬取 AcFun 排行榜（默认：全站综合，全部，今日）
     *
     * @param num 爬取条数
     */
    public void botAcfunrkTask(Integer num) {
        String url = ACFUN_RK_URL(num);
        try {
            String response = HttpUtil.httpGetJson(url);
            JSONArray array = (JSONArray) JSON.parseObject(response).get("rankList");
            assert array.size() == num;
            List<AcfunrkEntity> list = new ArrayList<>();
            LocalDateTime createTime = LocalDateTime.now();
            for (int i = 0; i < array.size(); i++) {
                JSONObject o = (JSONObject) array.get(i);

                String ranking = String.valueOf(i + 1);
                String dougaId = String.valueOf(o.get("dougaId"));
                String title = String.valueOf(o.get("title"));
                String contentDesc = String.valueOf(o.get("contentDesc"));
                String userId = String.valueOf(o.get("userId"));
                String userName = String.valueOf(o.get("userName"));
                String channel = o.get("channelId") + "/" + o.get("channelName");
                LocalDateTime contributeTime = DateUtil.toLocalDateTime(
                        Long.valueOf(String.valueOf(o.get("contributeTime"))));
                String fansCount = String.valueOf(o.get("fansCount"));
                String contributionCount = String.valueOf(o.get("contributionCount"));
                String viewCount = String.valueOf(o.get("viewCount"));
                String danmuCount = String.valueOf(o.get("danmuCount"));
                String commentCount = String.valueOf(o.get("commentCount"));
                String stowCount = String.valueOf(o.get("stowCount"));
                String bananaCount = String.valueOf(o.get("bananaCount"));
                String shareCount = String.valueOf(o.get("shareCount"));

                AcfunrkEntity entity = new AcfunrkEntity();
                entity.setRanking(ranking);
                entity.setDougaId(dougaId);
                entity.setTitle(title);
                entity.setContentDesc(StringUtils.substring(contentDesc, 0, 1000)); // 数据库最大长度 1K
                entity.setUserId(userId);
                entity.setUserName(userName);
                entity.setChannel(channel);
                entity.setContributeTime(contributeTime);
                entity.setFansCount(fansCount);
                entity.setContributionCount(contributionCount);
                entity.setViewCount(viewCount);
                entity.setDanmuCount(danmuCount);
                entity.setStowCount(stowCount);
                entity.setCommentCount(commentCount);
                entity.setStowCount(stowCount);
                entity.setBananaCount(bananaCount);
                entity.setShareCount(shareCount);
                entity.setCreateTime(createTime);
                list.add(entity);
            }

            acfunrkService.saveBatch(list);
            log.info("[acfunTask.botAcfunrkTask] 定时任务执行完成");
        } catch (Exception e) {
            log.error("[acfunTask.botAcfunrkTask] 定时任务发生错误");
            log.error(e.getMessage());
            throw new CommonException("[acfunTask.botAcfunrkTask] 定时任务发生错误", e);
        }
    }
}
