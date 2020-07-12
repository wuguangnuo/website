package cn.wgn.framework.web.domain;

import cn.wgn.framework.utils.StringUtil;

/**
 * 分页信息数据
 *
 * @author WuGuangNuo
 * @date Created in 2020/6/14 14:33
 */
public class PageDomain extends BaseDto{
    /**
     * 当前记录起始索引
     */
    private Integer pageNum;
    /**
     * 每页显示记录数
     */
    private Integer pageSize;
    /**
     * 排序列
     */
    private String orderByColumn;
    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    private String orderBySort;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderByColumn() {
        return orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn) {
        this.orderByColumn = orderByColumn;
    }

    public String getOrderBySort() {
        return orderBySort;
    }

    public void setOrderBySort(String orderBySort) {
        this.orderBySort = orderBySort;
    }

    /**
     * 获取 OrderBy 信息
     *
     * @return
     */
    public String getOrderBy() {
        if (StringUtil.isEmpty(orderByColumn)) {
            return "";
        }
        return StringUtil.toUnderScoreCase(orderByColumn) + " " + orderBySort;
    }
}
