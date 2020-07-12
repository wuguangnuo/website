package cn.wgn.website.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import cn.wgn.framework.web.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.Version;
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
 * @since 2020-06-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wu_demo")
@ApiModel(value="DemoEntity对象", description="諾的DEMO")
public class DemoEntity extends BaseEntity<DemoEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "demo名称")
    private String demoTitle;

    @ApiModelProperty(value = "demo作者")
    private String demoAuthor;

    @ApiModelProperty(value = "demo链接")
    private String demoLink;

    @ApiModelProperty(value = "demo图片")
    private String demoImg;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
