package cn.wgn.website.sys.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.wgn.framework.web.entity.BaseEntity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 开发文档
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-21
 */
@TableName("wu_doc")
@ApiModel(value="DocEntity对象", description="开发文档")
public class DocEntity extends BaseEntity<DocEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文档标题")
    private String docTitle;

    @ApiModelProperty(value = "文档价格")
    private BigDecimal docPrice;

    @ApiModelProperty(value = "文档链接")
    private String docLink;

    @ApiModelProperty(value = "文档图片")
    private String docImg;


    @Override
    protected Serializable pkVal() {
        return null;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public BigDecimal getDocPrice() {
        return docPrice;
    }

    public void setDocPrice(BigDecimal docPrice) {
        this.docPrice = docPrice;
    }

    public String getDocLink() {
        return docLink;
    }

    public void setDocLink(String docLink) {
        this.docLink = docLink;
    }

    public String getDocImg() {
        return docImg;
    }

    public void setDocImg(String docImg) {
        this.docImg = docImg;
    }
}
