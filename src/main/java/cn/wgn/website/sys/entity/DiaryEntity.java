package cn.wgn.website.sys.entity;

import cn.wgn.framework.web.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 諾的日记
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-21
 */
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

    public String getDiaryTitle() {
        return diaryTitle;
    }

    public void setDiaryTitle(String diaryTitle) {
        this.diaryTitle = diaryTitle;
    }

    public String getDiaryKey() {
        return diaryKey;
    }

    public void setDiaryKey(String diaryKey) {
        this.diaryKey = diaryKey;
    }

    public String getDiaryContent() {
        return diaryContent;
    }

    public void setDiaryContent(String diaryContent) {
        this.diaryContent = diaryContent;
    }

    public LocalDateTime getDiaryDate() {
        return diaryDate;
    }

    public void setDiaryDate(LocalDateTime diaryDate) {
        this.diaryDate = diaryDate;
    }
}
