package cn.wgn.website.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 选择选项
 *
 * @author WuGuangNuo
 * @date Created in 2020/2/22 23:03
 */
@Data
@Accessors(chain = true)
public class SelectListItem {
    @ApiModelProperty(value = "文本")
    private String text;
    @ApiModelProperty(value = "值")
    private String value;
}
