package cn.wgn.website.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.wgn.framework.web.entity.BaseEntity;

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
 * @since 2020-06-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wu_game")
@ApiModel(value="GameEntity对象", description="諾的H5游戏")
public class GameEntity extends BaseEntity<GameEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "游戏名称")
    private String gameTitle;

    @ApiModelProperty(value = "游戏作者")
    private String gameAuthor;

    @ApiModelProperty(value = "游戏链接")
    private String gameLink;

    @ApiModelProperty(value = "游戏图片")
    private String gameImg;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
