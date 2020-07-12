package cn.wgn.framework.aspectj.annotation;

import java.lang.annotation.*;

/**
 * AOP 自定义注解权限控制
 *
 * @author WuGuangNuo
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authorize {
    /**
     * 访问权限，默认为空，登录即可访问
     * 可以多个定义
     *
     * @return
     */
    String[] value() default {};
}