package cn.wgn.website.sys.entity;

import cn.wgn.framework.web.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 分享系统表
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-27
 */
@TableName("wu_share")
@ApiModel(value="ShareEntity对象", description="分享系统表")
public class ShareEntity extends BaseEntity<ShareEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件名")
    private String name;

    @ApiModelProperty(value = "文件类型")
    private String contentType;

    @ApiModelProperty(value = "文件大小")
    private Long contentLength;

    @ApiModelProperty(value = "文件最后上传时间")
    private Date lastModified;

    @ApiModelProperty(value = "状态(0分享取消,1正常,2禁用)")
    private String status;

    @ApiModelProperty(value = "请求成功次数")
    private Long successNum;

    @ApiModelProperty(value = "请求总数")
    private Long requestNum;


    @Override
    protected Serializable pkVal() {
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getContentLength() {
        return contentLength;
    }

    public void setContentLength(Long contentLength) {
        this.contentLength = contentLength;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSuccessNum() {
        return successNum;
    }

    public void setSuccessNum(Long successNum) {
        this.successNum = successNum;
    }

    public Long getRequestNum() {
        return requestNum;
    }

    public void setRequestNum(Long requestNum) {
        this.requestNum = requestNum;
    }
}
