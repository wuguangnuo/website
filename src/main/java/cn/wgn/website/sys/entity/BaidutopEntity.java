package cn.wgn.website.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

/**
 * <p>
 * 百度风云榜实时热点排行榜
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-09-06
 */
@TableName("bot_baidutop")
@ApiModel(value = "BaidutopEntity对象", description = "百度风云榜实时热点排行榜")
public class BaidutopEntity {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "排名")
    private String ranking;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "搜索指数")
    private String score;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "趋势")
    private String trend;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
