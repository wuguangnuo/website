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
 * 爬虫哔哩排行榜
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-21
 */
@Data
@Accessors(chain = true)
@TableName("bot_bilirk")
@ApiModel(value="BilirkEntity对象", description="爬虫哔哩排行榜")
public class BilirkEntity  {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
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
    private LocalDateTime createTime;
}
