package cn.wgn.framework.aspectj;

import cn.wgn.framework.aspectj.annotation.Authorize;
import cn.wgn.framework.constant.MagicValue;
import cn.wgn.framework.utils.StringUtil;
import cn.wgn.framework.utils.TokenUtil;
import cn.wgn.framework.utils.servlet.ServletUtil;
import cn.wgn.framework.web.ApiRes;
import cn.wgn.framework.web.domain.UserData;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

/**
 * Spring Aop(aspect)
 * AOP 自定义注解权限控制
 *
 * @author WuGuangNuo
 */
@Order(10)
@Aspect
@Component
public class AuthorizeAspect {

    /**
     * Spring中使用@Pointcut注解来定义方法切入点
     *
     * @Pointcut 用来定义切点，针对方法
     * @Aspect 用来定义切面，针对类
     * 后面的增强均是围绕此切入点来完成的
     */
    @Around(value = "@annotation(authorize)")
    public Object deBefore(ProceedingJoinPoint pjp, Authorize authorize) throws Throwable {
        // 获取token
        String token = ServletUtil.getHeader(MagicValue.TOKEN);
        // 未登录
        if (StringUtil.isEmpty(token)) {
            return ApiRes.unAuthorized();
        }

        UserData userData = TokenUtil.getUserData();
        // 获取注解
        String[] codes = authorize.value();

        if (userData == null || userData.getId() == null) {
            return ApiRes.unAuthorized("登录信息过期，请重新登录");
        }
        if (codes.length == 0) {
            // 注解为空,登陆即可
            return pjp.proceed();
        } else {
            // 登录者的所有权限
            Set<String> powers = userData.getPermissions();

            for (String code : codes) {
                if (powers.contains(code)) {
                    // 身份匹配成功
                    return pjp.proceed();
                }
            }

            return ApiRes.unAuthorized("权限不足：" + Arrays.toString(codes));
        }
    }

}