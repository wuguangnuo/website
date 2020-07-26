package cn.wgn.website.sys.enums;

import cn.wgn.framework.web.enums.BaseEnum;

/**
 * 状态枚举
 *
 * @author WuGuangNuo
 * @date Created in 2020/7/8 22:05
 */
public enum NovelStateEnum implements BaseEnum<String> {
    //
    NOTHING("0", "不存在"),
    NORMAL("1", "正常"),
    DELETE("2", "已删除"),
    //
    PRIVATE("11", "自己可见"),
    PUBLIC("12", "所有人可见"),
    //
    OTHER("99", "其他");

    private final String value;
    private final String desc;

    NovelStateEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * Get Enum Value
     *
     * @return T(General : String, Integer, Long...)
     */
    @Override
    public String getValue() {
        return this.value;
    }

    /**
     * Description
     *
     * @return String
     */
    @Override
    public String getDesc() {
        return this.desc;
    }
}
