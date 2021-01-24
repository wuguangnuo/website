package cn.wgn.framework.web.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-21
 */
@TableName("wu_dictionary")
@ApiModel(value="DictionaryEntity对象", description="字典表")
public class DictionaryEntity extends BaseEntity<DictionaryEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典类型")
    private String groupKey;

    @ApiModelProperty(value = "字典索引")
    private String codeIndex;

    @ApiModelProperty(value = "字典值")
    private String codeValue;

    @ApiModelProperty(value = "字典注释")
    private String codeNote;

    @Override
    protected Serializable pkVal() {
        return null;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getCodeIndex() {
        return codeIndex;
    }

    public void setCodeIndex(String codeIndex) {
        this.codeIndex = codeIndex;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public String getCodeNote() {
        return codeNote;
    }

    public void setCodeNote(String codeNote) {
        this.codeNote = codeNote;
    }
}
