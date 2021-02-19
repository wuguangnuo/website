package cn.wgn.website.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

/**
 * <p>
 * 草榴社區
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-09-19
 */
@TableName("bot_clav")
@ApiModel(value = "ClavEntity对象", description = "草榴社區")
public class ClavEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "分区")
    private String part;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "上传时间")
    private String uploadTime;

    @ApiModelProperty(value = "回复数")
    private String replyNum;

    @ApiModelProperty(value = "下载量")
    private String downloadNum;

    @ApiModelProperty(value = "详情页地址")
    private String url;

    @ApiModelProperty(value = "种子路径")
    private String btPath;

    @ApiModelProperty(value = "执行状态（0正常 1失败）")
    private String status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(String replyNum) {
        this.replyNum = replyNum;
    }

    public String getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(String downloadNum) {
        this.downloadNum = downloadNum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBtPath() {
        return btPath;
    }

    public void setBtPath(String btPath) {
        this.btPath = btPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
