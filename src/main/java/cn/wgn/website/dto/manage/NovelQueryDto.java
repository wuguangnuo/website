package cn.wgn.website.dto.manage;

import cn.wgn.website.dto.RequestPage;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

/**
 * 小说列表查询DTO
 *
 * @author WuGuangNuo
 * @date Created in 2020/2/25 23:45
 */
public class NovelQueryDto extends RequestPage {
//    @ApiModelProperty(value = "小说ID")
//    private Integer id;
    @ApiModelProperty(value = "小说标题")
    private String novelTitle;
    @ApiModelProperty(value = "小说类型")
    private String novelType;
    @ApiModelProperty(value = "创建时间1")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTm1;
    @ApiModelProperty(value = "创建时间2")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTm2;
    @ApiModelProperty(value = "更新时间1")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTm1;
    @ApiModelProperty(value = "更新时间2")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTm2;


    @ApiModelProperty("排序方式，形如(id ASC)")
    private String orderBy;
    @ApiModelProperty("导出格式(填写 Excel 即导出表格)")
    private String export;

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }

    public String getNovelTitle() {
        return novelTitle;
    }

    public void setNovelTitle(String novelTitle) {
        this.novelTitle = novelTitle;
    }

    public String getNovelType() {
        return novelType;
    }

    public void setNovelType(String novelType) {
        this.novelType = novelType;
    }

    public LocalDateTime getCreateTm1() {
        return createTm1;
    }

    public void setCreateTm1(LocalDateTime createTm1) {
        this.createTm1 = createTm1;
    }

    public LocalDateTime getCreateTm2() {
        return createTm2;
    }

    public void setCreateTm2(LocalDateTime createTm2) {
        this.createTm2 = createTm2;
    }

    public LocalDateTime getUpdateTm1() {
        return updateTm1;
    }

    public void setUpdateTm1(LocalDateTime updateTm1) {
        this.updateTm1 = updateTm1;
    }

    public LocalDateTime getUpdateTm2() {
        return updateTm2;
    }

    public void setUpdateTm2(LocalDateTime updateTm2) {
        this.updateTm2 = updateTm2;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getExport() {
        return export;
    }

    public void setExport(String export) {
        this.export = export;
    }
}
