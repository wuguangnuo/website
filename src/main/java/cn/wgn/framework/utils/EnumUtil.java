package cn.wgn.framework.utils;

import cn.wgn.framework.web.enums.BaseEnum;

/**
 * 枚举工具类
 *
 * @author WuGuangNuo
 * @date Created in 2020/8/2 21:19
 */
public class EnumUtil {
    /**
     * 获取枚举值
     *
     * @param value 枚举值
     * @param clazz Enum.class
     * @param <T>   Enum
     * @return 枚举值
     */
    public static <T extends BaseEnum> T getEnumByValue(String value, Class<T> clazz) {
        for (T e : clazz.getEnumConstants()) {
            if (value.equals(e.getValue())) {
                return e;
            }
        }
        return null;
    }

    public static <T extends Enum<?>> T getEnumByName(String name, Class<T> clazz) {
        if (!clazz.isEnum()) {
            return null;
        }
        try {
            T[] enumConstants = clazz.getEnumConstants();
            for (T ec : enumConstants) {
                if (((Enum<?>) ec).name().equals(name)) {
                    return ec;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T extends Enum<?>, S extends Enum<?>> T convertEnum(S source, Class<T> targetClass) {
        if (source instanceof Enum) {
            String sourceEnum = ((Enum<?>) source).name();
            try {
                return getEnumByName(sourceEnum, targetClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }
}
