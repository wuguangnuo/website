package cn.wgn.website.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 諾的日记
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-02-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wu_diary")
@ApiModel(value = "Diary对象", description = "諾的日记")
public class DiaryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "日记标题")
    private String diaryTitle;

    @ApiModelProperty(value = "日记关键词")
    private String diaryKey;

    @ApiModelProperty(value = "日记内容")
    private String diaryContent;

    @ApiModelProperty(value = "发布日期")
    private LocalDateTime diaryDate;
}
