package cn.wgn.framework.web.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
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

}
