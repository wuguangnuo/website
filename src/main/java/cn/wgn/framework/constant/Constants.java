package cn.wgn.framework.constant;

/**
 * 通用常量信息
 *
 * @author WuGuangNuo
 * @date Created in 2020/6/7 20:53
 */
public class Constants {
    /**
     * Redis过期时间,一周(秒)
     */
    public static final Integer WEEK_EXPIRE = 7 * 24 * 3600;
    /**
     * 默认分页第几页
     */
    public static final Integer DEFAULT_PAGE_NUM = 1;
    /**
     * 默认分页大小
     */
    public static final Integer DEFAULT_PAGE_SIZE = 10;
    /**
     * 最大分页大小
     */
    public static final Integer MAX_PAGE_SIZE = 500;
    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";
    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";
    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";
    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String ORDER_BY_SORT = "orderBySort";
    /**
     * 排序的方向 从小到大
     */
    public static final String ASC = "ASC";
    /**
     * 排序的方向 从大到小
     */
    public static final String DESC = "DESC";

}
