package cn.wgn.website.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import cn.wgn.framework.web.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.Version;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2020-06-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wu_diary")
@ApiModel(value="DiaryEntity对象", description="諾的日记")
public class DiaryEntity extends BaseEntity<DiaryEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "日记标题")
    private String diaryTitle;

    @ApiModelProperty(value = "日记关键词")
    private String diaryKey;

    @ApiModelProperty(value = "日记内容")
    private String diaryContent;

    @ApiModelProperty(value = "发布日期")
    private LocalDateTime diaryDate;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
