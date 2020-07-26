package cn.wgn.website.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.wgn.framework.web.entity.BaseEntity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 小说表
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wu_novel")
@ApiModel(value="NovelEntity对象", description="小说表")
public class NovelEntity extends BaseEntity<NovelEntity> {

    private static final long serialVersionUID = 1L;

    private String novelTitle;

    private String novelAuthor;

    private String novelType;

    private String novelContent;

    private String status;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
