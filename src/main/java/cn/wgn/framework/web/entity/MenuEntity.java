package cn.wgn.framework.web.entity;

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
 * 菜单表
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wu_menu")
@ApiModel(value="MenuEntity对象", description="菜单表")
public class MenuEntity extends BaseEntity<MenuEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "代码")
    private String code;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "路径")
    private String url;

    @ApiModelProperty(value = "名称")
    private String name;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
