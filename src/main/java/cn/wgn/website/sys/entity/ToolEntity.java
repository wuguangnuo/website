package cn.wgn.website.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.wgn.framework.web.entity.BaseEntity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 諾的工具箱
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wu_tool")
@ApiModel(value="ToolEntity对象", description="諾的工具箱")
public class ToolEntity extends BaseEntity<ToolEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "工具名称")
    private String toolTitle;

    @ApiModelProperty(value = "工具作者")
    private String toolAuthor;

    @ApiModelProperty(value = "原链接")
    private String toolFrom;

    @ApiModelProperty(value = "工具链接")
    private String toolLink;

    @ApiModelProperty(value = "工具图片")
    private String toolImg;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
