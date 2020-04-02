package cn.wgn.website.dto.manage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/23 0:48
 */
@Data
@Accessors(chain = true)
public class NovelDto {
    @ApiModelProperty(value = "小说ID")
    private Long id;

    @ApiModelProperty(value = "小说标题")
    private String novelTitle;

    @ApiModelProperty(value = "小说类型(11私有,12公共)")
    private String novelState;

    @ApiModelProperty(value = "小说内容")
    private String novelContent;
}
