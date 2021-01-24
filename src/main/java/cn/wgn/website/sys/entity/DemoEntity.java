package cn.wgn.website.sys.entity;

import cn.wgn.framework.web.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 諾的DEMO
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-21
 */
@TableName("wu_demo")
@ApiModel(value="DemoEntity对象", description="諾的DEMO")
public class DemoEntity extends BaseEntity<DemoEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "demo名称")
    private String demoTitle;

    @ApiModelProperty(value = "demo作者")
    private String demoAuthor;

    @ApiModelProperty(value = "demo链接")
    private String demoLink;

    @ApiModelProperty(value = "demo图片")
    private String demoImg;


    @Override
    protected Serializable pkVal() {
        return null;
    }

    public String getDemoTitle() {
        return demoTitle;
    }

    public void setDemoTitle(String demoTitle) {
        this.demoTitle = demoTitle;
    }

    public String getDemoAuthor() {
        return demoAuthor;
    }

    public void setDemoAuthor(String demoAuthor) {
        this.demoAuthor = demoAuthor;
    }

    public String getDemoLink() {
        return demoLink;
    }

    public void setDemoLink(String demoLink) {
        this.demoLink = demoLink;
    }

    public String getDemoImg() {
        return demoImg;
    }

    public void setDemoImg(String demoImg) {
        this.demoImg = demoImg;
    }
}
