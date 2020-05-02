package cn.wgn.website.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 爬虫_bili在线列表
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bot_biliol")
@ApiModel(value = "BotBiliolEntity对象", description = "")
public class BotBiliolEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "作者id")
    private String uid;

    @ApiModelProperty(value = "链接")
    private String link;

    @ApiModelProperty(value = "播放量")
    private String playNum;

    @ApiModelProperty(value = "弹幕量")
    private String dmNum;

    @ApiModelProperty(value = "当前在线量")
    private String olNum;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTm;


}
