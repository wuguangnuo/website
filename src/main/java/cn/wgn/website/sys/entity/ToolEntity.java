package cn.wgn.website.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.wgn.framework.web.entity.BaseEntity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 諾的工具箱
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-21
 */
@TableName("wu_tool")
@ApiModel(value="ToolEntity对象", description="諾的工具箱")
public class ToolEntity extends BaseEntity<ToolEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "工具名称")
    private String toolTitle;

    @ApiModelProperty(value = "工具作者")
    private String toolAuthor;

    @ApiModelProperty(value = "原链接")
    private String toolFrom;

    @ApiModelProperty(value = "工具链接")
    private String toolLink;

    @ApiModelProperty(value = "工具图片")
    private String toolImg;


    @Override
    protected Serializable pkVal() {
        return null;
    }

    public String getToolTitle() {
        return toolTitle;
    }

    public void setToolTitle(String toolTitle) {
        this.toolTitle = toolTitle;
    }

    public String getToolAuthor() {
        return toolAuthor;
    }

    public void setToolAuthor(String toolAuthor) {
        this.toolAuthor = toolAuthor;
    }

    public String getToolFrom() {
        return toolFrom;
    }

    public void setToolFrom(String toolFrom) {
        this.toolFrom = toolFrom;
    }

    public String getToolLink() {
        return toolLink;
    }

    public void setToolLink(String toolLink) {
        this.toolLink = toolLink;
    }

    public String getToolImg() {
        return toolImg;
    }

    public void setToolImg(String toolImg) {
        this.toolImg = toolImg;
    }
}
