package cn.wgn.framework.aspectj;

import cn.wgn.framework.aspectj.annotation.Authorize;
import cn.wgn.framework.exception.CommonException;
import cn.wgn.framework.utils.RedisUtil;
import cn.wgn.framework.web.ApiRes;
import cn.wgn.framework.web.domain.UserData;
import cn.wgn.framework.web.entity.RoleMenuEntity;
import cn.wgn.framework.web.enums.RedisPrefixKeyEnum;
import cn.wgn.framework.web.service.IRoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

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
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IRoleMenuService roleMenuService;

    /**
     * Spring中使用@Pointcut注解来定义方法切入点
     *
     * @Pointcut 用来定义切点，针对方法
     * @Aspect 用来定义切面，针对类
     * 后面的增强均是围绕此切入点来完成的
     */
    @Around(value = "@annotation(authorize)")
    public Object deBefore(ProceedingJoinPoint pjp, Authorize authorize) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null : new CommonException("ServletRequest Null Error!");
        HttpServletRequest request = attributes.getRequest();
        // 获取token
        String token = request.getHeader("token");
        // 获取注解
        String[] codes = authorize.value();

        // 未登录
        if (Strings.isNullOrEmpty(token) || !redisUtil.exists(token, RedisPrefixKeyEnum.TOKEN.getValue())) {
            return ApiRes.unAuthorized();
        }

        String[] arr = redisUtil.get(token, RedisPrefixKeyEnum.TOKEN.getValue()).split(":");
        if (codes.length == 0) {
            // 注解为空,登陆即可
            if (arr.length == 3) {
                UserData userData = new UserData();
                userData.setId(Long.valueOf(arr[0]));
                userData.setAccount(arr[1]);
                userData.setRoleId(Long.valueOf(arr[2]));
                request.setAttribute("userData", userData);
            }
            return pjp.proceed();
        } else {
            int roleId = Integer.parseInt(arr[2]);
            // roleId 的所有权限
            List<RoleMenuEntity> powers = roleMenuService.list(
                    new QueryWrapper<RoleMenuEntity>().lambda()
                            .eq(RoleMenuEntity::getRoleId, roleId)
            );

            for (String code : codes) {
                for (RoleMenuEntity power : powers) {
                    if (code.equals(power.getMenuCode())) {
                        // 身份匹配成功
                        if (arr.length == 3) {
                            UserData userData = new UserData();
                            userData.setId(Long.valueOf(arr[0]));
                            userData.setAccount(arr[1]);
                            userData.setRoleId(Long.valueOf(arr[2]));
                            request.setAttribute("userData", userData);
                        }
                        return pjp.proceed();
                    }
                }
            }

            return ApiRes.unAuthorized("权限不足：" + Arrays.toString(codes));
        }
    }

}