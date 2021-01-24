package cn.wgn.website.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

/**
 * <p>
 * A站全站综合排行榜
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-07-25
 */
@TableName("bot_acfunrk")
@ApiModel(value="AcfunrkEntity对象", description="A站全站综合排行榜")
public class AcfunrkEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "排名")
    private String ranking;

    @ApiModelProperty(value = "动画id(ac号)")
    private String dougaId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "视频简介")
    private String contentDesc;

    @ApiModelProperty(value = "作者id")
    private String userId;

    @ApiModelProperty(value = "作者")
    private String userName;

    @ApiModelProperty(value = "分类频道")
    private String channel;

    @ApiModelProperty(value = "投稿时间")
    private LocalDateTime contributeTime;

    @ApiModelProperty(value = "粉丝数")
    private String fansCount;

    @ApiModelProperty(value = "投稿量")
    private String contributionCount;

    @ApiModelProperty(value = "播放量")
    private String viewCount;

    @ApiModelProperty(value = "弹幕量")
    private String danmuCount;

    @ApiModelProperty(value = "评论数")
    private String commentCount;

    @ApiModelProperty(value = "收藏数")
    private String stowCount;

    @ApiModelProperty(value = "香蕉数")
    private String bananaCount;

    @ApiModelProperty(value = "分享数")
    private String shareCount;

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

    public String getDougaId() {
        return dougaId;
    }

    public void setDougaId(String dougaId) {
        this.dougaId = dougaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentDesc() {
        return contentDesc;
    }

    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public LocalDateTime getContributeTime() {
        return contributeTime;
    }

    public void setContributeTime(LocalDateTime contributeTime) {
        this.contributeTime = contributeTime;
    }

    public String getFansCount() {
        return fansCount;
    }

    public void setFansCount(String fansCount) {
        this.fansCount = fansCount;
    }

    public String getContributionCount() {
        return contributionCount;
    }

    public void setContributionCount(String contributionCount) {
        this.contributionCount = contributionCount;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getDanmuCount() {
        return danmuCount;
    }

    public void setDanmuCount(String danmuCount) {
        this.danmuCount = danmuCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getStowCount() {
        return stowCount;
    }

    public void setStowCount(String stowCount) {
        this.stowCount = stowCount;
    }

    public String getBananaCount() {
        return bananaCount;
    }

    public void setBananaCount(String bananaCount) {
        this.bananaCount = bananaCount;
    }

    public String getShareCount() {
        return shareCount;
    }

    public void setShareCount(String shareCount) {
        this.shareCount = shareCount;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
