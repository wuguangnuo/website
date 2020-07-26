package cn.wgn.website.sys.entity;

import cn.wgn.framework.web.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
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

}
