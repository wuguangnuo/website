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
public class DemoDto {

    @ApiModelProperty(value = "demo名称")
    private String demoTitle;

    @ApiModelProperty(value = "demo作者")
    private String demoAuthor;

    @ApiModelProperty(value = "demo链接")
    private String demoLink;

    @ApiModelProperty(value = "demo图片")
    private String demoImg;
}
