package cn.wgn.website.dto.manage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 小说返回对象
 *
 * @author WuGuangNuo
 * @date Created in 2020/2/26 0:08
 */
@Data
public class Novel {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "小说标题")
    private String novelTitle;

    @ApiModelProperty(value = "小说作者")
    private String novelAuthor;

    @ApiModelProperty(value = "小说类型")
    private String novelType;

    @ApiModelProperty(value = "小说状态")
    private String novelState;

    @ApiModelProperty(value = "小说内容")
    private String novelContent;

    @ApiModelProperty(value = "小说创建时间")
    private LocalDateTime createTm;

    @ApiModelProperty(value = "小说更新时间")
    private LocalDateTime updateTm;
}
