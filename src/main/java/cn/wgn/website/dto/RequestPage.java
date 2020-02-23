package cn.wgn.website.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/16 17:37
 */
public class RequestPage {
    @ApiModelProperty(value = "分页大小")
    private int pageSize;

    @ApiModelProperty(value = "分页页码(第一页填1)")
    private int pageIndex;

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    // 默认分页大小
    public int getPageSize() {
        return pageSize <= 0 ? 8 : pageSize;
    }

    // 默认分页第几页
    public int getPageIndex() {
        return pageIndex <= 0 ? 1 : pageIndex;
    }
}
