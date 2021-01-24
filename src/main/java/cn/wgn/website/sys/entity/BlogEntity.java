package cn.wgn.website.sys.entity;

import cn.wgn.framework.web.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 諾的博客
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-21
 */
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

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostAuthor() {
        return postAuthor;
    }

    public void setPostAuthor(String postAuthor) {
        this.postAuthor = postAuthor;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public LocalDateTime getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDateTime postDate) {
        this.postDate = postDate;
    }

    public String getPostFrom() {
        return postFrom;
    }

    public void setPostFrom(String postFrom) {
        this.postFrom = postFrom;
    }

    public String getPostLink() {
        return postLink;
    }

    public void setPostLink(String postLink) {
        this.postLink = postLink;
    }
}
