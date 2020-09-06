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
 * 百度风云榜实时热点排行榜
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-09-06
 */
@Data
@Accessors(chain = true)
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

}
