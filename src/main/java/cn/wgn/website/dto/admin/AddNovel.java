package cn.wgn.website.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/23 0:48
 */
@Data
@Accessors(chain = true)
public class AddNovel {
    @ApiModelProperty(value = "小说ID")
    private Integer id;

    @ApiModelProperty(value = "小说标题")
    private String novelTitle;

    @ApiModelProperty(value = "小说内容")
    private String novelContent;
}
