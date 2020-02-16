package cn.wgn.website.dto.home;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/15 21:16
 */
@Data
@Accessors(chain = true)
public class DiaryDto {

    @ApiModelProperty(value = "日记标题")
    private String diaryTitle;

    @ApiModelProperty(value = "日记关键词")
    private String diaryKey;

    @ApiModelProperty(value = "日记内容")
    private String diaryContent;

    @ApiModelProperty(value = "发布日期")
    private LocalDateTime diaryDate;
}
