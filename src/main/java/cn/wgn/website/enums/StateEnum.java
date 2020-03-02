package cn.wgn.website.enums;

/**
 * 通用状态枚举
 *
 * @author WuGuangNuo
 * @date Created in 2020/3/2 23:43
 */
public enum StateEnum {
    NOTHING("不存在", "0"),
    NORMAL("正常", "1"),
    DELETE("删除", "2"),
    OTHER("其他", "99");

    String label;
    String value;

    StateEnum(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 根据给定的value查找对应的label
     *
     * @param value 给定的value，支持null
     * @return 对应的label，没有找到或给定的value为null都返回""空字符串
     */
    public static String getLabel(String value) {
        if (value != null) {
            for (StateEnum s : StateEnum.values()) {
                if (s.getValue().equals(value)) {
                    return s.getLabel();
                }
            }
        }
        return "";
    }
}
