package cn.wgn.website.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * A站全站综合排行榜
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-07-25
 */
@Data
@Accessors(chain = true)
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

}
