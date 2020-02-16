package cn.wgn.website.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 諾的H5游戏
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-02-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wu_game")
@ApiModel(value="Game对象", description="諾的H5游戏")
public class GameEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "游戏名称")
    private String gameTitle;

    @ApiModelProperty(value = "游戏作者")
    private String gameAuthor;

    @ApiModelProperty(value = "游戏链接")
    private String gameLink;

    @ApiModelProperty(value = "游戏图片")
    private String gameImg;
}
