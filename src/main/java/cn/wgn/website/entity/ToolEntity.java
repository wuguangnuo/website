package cn.wgn.website.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2020-02-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wu_tool")
@ApiModel(value="Tool对象", description="諾的工具箱")
public class ToolEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
}
