package cn.wgn.website.dto.home;

import cn.wgn.website.dto.RequestPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/16 17:46
 */
public class BlogListQuery extends RequestPage {

    @ApiModelProperty(value = "文章类型")
    private String type;
    @ApiModelProperty(value = "关键词")
    private String keyword;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
