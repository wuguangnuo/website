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
 * 諾的博客
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-02-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wu_blog")
@ApiModel(value="Blog对象", description="諾的博客")
public class BlogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "文章标题")
    private String postTitle;

    @ApiModelProperty(value = "文章作者")
    private String postAuthor;

    @ApiModelProperty(value = "文章分类")
    private String postType;

    @ApiModelProperty(value = "文章内容")
    private String postContent;

    @ApiModelProperty(value = "发布日期")
    private LocalDateTime postDate;

    @ApiModelProperty(value = "文章来源")
    private String postFrom;

    @ApiModelProperty(value = "原链接")
    private String postLink;


}
