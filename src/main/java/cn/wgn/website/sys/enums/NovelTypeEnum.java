package cn.wgn.website.sys.enums;

import cn.wgn.framework.web.enums.BaseEnum;

/**
 * @author WuGuangNuo
 * @date Created in 2020/7/8 22:14
 */
public enum NovelTypeEnum implements BaseEnum<String> {
    // 普通html
    Html("HTML", "Html"),
    // Markdown
    Markdown("MARKDOWN", "Markdown");

    private final String value;
    private final String desc;

    NovelTypeEnum(String value, String desc) {
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
