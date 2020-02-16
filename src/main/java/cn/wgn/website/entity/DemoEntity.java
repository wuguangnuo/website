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
 * 諾的DEMO
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-02-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wu_demo")
@ApiModel(value="Demo对象", description="諾的DEMO")
public class DemoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "demo名称")
    private String demoTitle;

    @ApiModelProperty(value = "demo作者")
    private String demoAuthor;

    @ApiModelProperty(value = "demo链接")
    private String demoLink;

    @ApiModelProperty(value = "demo图片")
    private String demoImg;
}
