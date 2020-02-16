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
public class DocDto {

    @ApiModelProperty(value = "文档标题")
    private String docTitle;

    @ApiModelProperty(value = "文档价格")
    private Double docPrice;

    @ApiModelProperty(value = "文档链接")
    private String docLink;

    @ApiModelProperty(value = "文档图片")
    private String docImg;
}
