package cn.wgn.website.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 小说表
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wu_novel")
@ApiModel(value="Novel对象", description="小说表")
public class NovelEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @ApiModelProperty(value = "小说标题")
    private String novelTitle;
    
    @ApiModelProperty(value = "小说作者")
    private String novelAuthor;
    
    @ApiModelProperty(value = "小说类型")
    private String novelType;
    
    @ApiModelProperty(value = "小说内容")
    private String novelContent;
    
    @ApiModelProperty(value = "小说创建时间")
    private LocalDateTime createTm;
    
    @ApiModelProperty(value = "小说更新时间")
    private LocalDateTime updateTm;


}
