package cn.wgn.website.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.wgn.framework.web.entity.BaseEntity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;

/**
 * <p>
 * 小说表
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-21
 */
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

    public String getNovelTitle() {
        return novelTitle;
    }

    public void setNovelTitle(String novelTitle) {
        this.novelTitle = novelTitle;
    }

    public String getNovelAuthor() {
        return novelAuthor;
    }

    public void setNovelAuthor(String novelAuthor) {
        this.novelAuthor = novelAuthor;
    }

    public String getNovelType() {
        return novelType;
    }

    public void setNovelType(String novelType) {
        this.novelType = novelType;
    }

    public String getNovelContent() {
        return novelContent;
    }

    public void setNovelContent(String novelContent) {
        this.novelContent = novelContent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
