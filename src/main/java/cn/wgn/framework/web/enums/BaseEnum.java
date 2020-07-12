package cn.wgn.framework.web.enums;

/**
 * @author WuGuangNuo
 * @date Created in 2020/6/7 11:36
 */
public abstract interface BaseEnum<T> {
    /**
     * Get Enum Value
     *
     * @return T(General : String, Integer, Long...)
     */
    T getValue();

    /**
     * Description
     *
     * @return String
     */
    String getDesc();
}
