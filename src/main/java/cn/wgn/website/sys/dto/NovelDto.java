package cn.wgn.website.sys.dto;

import cn.wgn.framework.web.domain.BaseDto;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author WuGuangNuo
 * @date Created in 2020/7/8 22:03
 */
public class NovelDto extends BaseDto {
    @ApiModelProperty(value = "小说ID")
    private Long id;
    @ApiModelProperty(value = "小说标题")
    private String novelTitle;
    @ApiModelProperty(value = "小说类型(11私有,12公共)")
    private String novelState;
    @ApiModelProperty(value = "小说内容")
    private String novelContent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNovelTitle() {
        return novelTitle;
    }

    public void setNovelTitle(String novelTitle) {
        this.novelTitle = novelTitle;
    }

    public String getNovelState() {
        return novelState;
    }

    public void setNovelState(String novelState) {
        this.novelState = novelState;
    }

    public String getNovelContent() {
        return novelContent;
    }

    public void setNovelContent(String novelContent) {
        this.novelContent = novelContent;
    }
}
