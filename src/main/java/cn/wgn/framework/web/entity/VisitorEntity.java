package cn.wgn.framework.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 访客统计表
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-07
 */
@Data
@Accessors(chain = true)
@TableName("wu_visitor")
@ApiModel(value = "VisitorEntity对象", description = "访客统计表")
public class VisitorEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "link")
    private String lk;

    @ApiModelProperty(value = "ip")
    private Integer ip;

    @ApiModelProperty(value = "agent")
    private String ag;

    @ApiModelProperty(value = "datetime")
    private LocalDateTime tm;

    @ApiModelProperty(value = "user")
    private String us;

}
