package cn.wgn.website.dto.home;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/16 21:29
 */
@Data
@Accessors(chain = true)
public class BlogDto {

    @ApiModelProperty(value = "主键")
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
    private String postDate;

    @ApiModelProperty(value = "文章来源")
    private String postFrom;

    @ApiModelProperty(value = "原链接")
    private String postLink;
}
