package cn.wgn.website.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 爬虫_bili排行榜
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bot_bilirk")
@ApiModel(value="BotBilirkEntity对象", description="爬虫_bili排行榜")
public class BotBilirkEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "排名")
    private String ranking;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "作者id")
    private String uid;

    @ApiModelProperty(value = "链接")
    private String link;

    @ApiModelProperty(value = "播放量")
    private String playNum;

    @ApiModelProperty(value = "弹幕量")
    private String dmNum;

    @ApiModelProperty(value = "综合得分")
    private String score;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTm;


}
