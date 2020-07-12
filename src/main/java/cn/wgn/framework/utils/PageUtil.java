package cn.wgn.framework.utils;

import cn.wgn.framework.constant.Constants;
import cn.wgn.framework.utils.servlet.ServletUtil;
import cn.wgn.framework.utils.sql.SqlUtil;
import cn.wgn.framework.web.domain.PageDomain;

/**
 * 获取请求分页信息
 *
 * @author WuGuangNuo
 * @date Created in 2020/6/14 14:18
 */
public class PageUtil {
    private static Integer DEFAULT_PAGE_NUM = Constants.DEFAULT_PAGE_NUM;
    private static Integer DEFAULT_PAGE_SIZE = Constants.DEFAULT_PAGE_SIZE;
    private static Integer MAX_PAGE_SIZE = Constants.MAX_PAGE_SIZE;
    private static String PAGE_NUM = Constants.PAGE_NUM;
    private static String PAGE_SIZE = Constants.PAGE_SIZE;
    private static String ORDER_BY_COLUMN = Constants.ORDER_BY_COLUMN;
    private static String ORDER_BY_SORT = Constants.ORDER_BY_SORT;
    private static String ASC = Constants.ASC;
    private static String DESC = Constants.DESC;

    /**
     * 默认分页
     *
     * @return
     */
    public static PageDomain defaultPage() {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(DEFAULT_PAGE_NUM);
        pageDomain.setPageSize(DEFAULT_PAGE_SIZE);
        pageDomain.setOrderByColumn(StringUtil.EMPTY);
        pageDomain.setOrderBySort(StringUtil.EMPTY);
        return pageDomain;
    }

    /**
     * 从request中获取分页信息
     *
     * @return
     */
    public static PageDomain getPage() {
        PageDomain pageDomain = new PageDomain();
        Integer pageNum = ServletUtil.getParameterToInt(PAGE_NUM);
        Integer pageSize = ServletUtil.getParameterToInt(PAGE_SIZE);
        String orderByColumn = ServletUtil.getParameter(ORDER_BY_COLUMN);
        String orderBySort = ServletUtil.getParameter(ORDER_BY_SORT);

        if (pageNum == null || pageNum < 0) {
            pageNum = DEFAULT_PAGE_NUM;
        }
        if (pageSize == null || pageSize < 0 || pageSize > MAX_PAGE_SIZE) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if (StringUtil.isNotNull(pageNum) && StringUtil.isNotNull(pageSize)) {
            orderByColumn = SqlUtil.escapeOrderBySql(orderByColumn);
            pageDomain.setOrderByColumn(orderByColumn);

            if (ASC.equalsIgnoreCase(orderBySort)) {
                pageDomain.setOrderBySort(ASC);
            } else if (DESC.equalsIgnoreCase(orderBySort)) {
                pageDomain.setOrderBySort(DESC);
            } else {
                pageDomain.setOrderBySort(StringUtil.EMPTY);
            }
        }
        pageDomain.setPageNum(pageNum);
        pageDomain.setPageSize(pageSize);
        return pageDomain;
    }
}
