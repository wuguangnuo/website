package cn.wgn.website.dto.home;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/16 15:22
 */
@Data
@Accessors(chain = true)
public class GameDto {

    @ApiModelProperty(value = "游戏名称")
    private String gameTitle;

    @ApiModelProperty(value = "游戏作者")
    private String gameAuthor;

    @ApiModelProperty(value = "游戏链接")
    private String gameLink;

    @ApiModelProperty(value = "游戏图片")
    private String gameImg;
}
