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
public class ToolDto {

    @ApiModelProperty(value = "工具名称")
    private String toolTitle;

    @ApiModelProperty(value = "工具作者")
    private String toolAuthor;

    @ApiModelProperty(value = "原链接")
    private String toolFrom;

    @ApiModelProperty(value = "工具链接")
    private String toolLink;

    @ApiModelProperty(value = "工具图片")
    private String toolImg;
}
