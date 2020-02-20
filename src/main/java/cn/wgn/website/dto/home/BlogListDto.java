package cn.wgn.website.dto.home;

import cn.wgn.website.entity.BlogEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 博客列表(临时兼容使用)
 *
 * @author WuGuangNuo
 * @date Created in 2020/2/19 11:54
 */
@Data
public class BlogListDto {
    @ApiModelProperty("分页信息")
    private String pageInfo;
    @ApiModelProperty("博文列表")
    private List blogList;
}
