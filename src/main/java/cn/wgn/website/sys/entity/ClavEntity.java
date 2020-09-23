package cn.wgn.website.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 草榴社區
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-09-19
 */
@Data
@Accessors(chain = true)
@TableName("bot_clav")
@ApiModel(value = "ClavEntity对象", description = "草榴社區")
public class ClavEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "分区")
    private String part;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "上传时间")
    private String uploadTime;

    @ApiModelProperty(value = "详情页地址")
    private String url;

    @ApiModelProperty(value = "种子路径")
    private String btPath;

    @ApiModelProperty(value = "执行状态（0正常 1失败）")
    private String status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
