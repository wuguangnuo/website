package cn.wgn.framework.web.enums;

/**
 * Redis类型枚举
 *
 * @author WuGuangNuo
 * @date Created in 2020/6/7 11:27
 */
public enum RedisPrefixKeyEnum implements BaseEnum<String> {
    // 登录令牌
    TOKEN("token", "登录令牌"),
    // 验证码
    CAPTCHA("Captcha", "验证码");

    private final String value;
    private final String desc;

    RedisPrefixKeyEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
