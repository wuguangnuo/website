package cn.wgn.website.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.wgn.framework.web.entity.BaseEntity;

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
 * @since 2020-06-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wu_blog")
@ApiModel(value="BlogEntity对象", description="諾的博客")
public class BlogEntity extends BaseEntity<BlogEntity> {

    private static final long serialVersionUID = 1L;

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


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
