package cn.wgn.framework.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

/**
 * <p>
 * 访客统计表
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-07
 */
@TableName("wu_visitor")
@ApiModel(value = "VisitorEntity对象", description = "访客统计表")
public class VisitorEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "link")
    private String lk;

    @ApiModelProperty(value = "ip")
    private Integer ip;

    @ApiModelProperty(value = "agent")
    private String ag;

    @ApiModelProperty(value = "datetime")
    private LocalDateTime tm;

    @ApiModelProperty(value = "user")
    private String us;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLk() {
        return lk;
    }

    public void setLk(String lk) {
        this.lk = lk;
    }

    public Integer getIp() {
        return ip;
    }

    public void setIp(Integer ip) {
        this.ip = ip;
    }

    public String getAg() {
        return ag;
    }

    public void setAg(String ag) {
        this.ag = ag;
    }

    public LocalDateTime getTm() {
        return tm;
    }

    public void setTm(LocalDateTime tm) {
        this.tm = tm;
    }

    public String getUs() {
        return us;
    }

    public void setUs(String us) {
        this.us = us;
    }
}
