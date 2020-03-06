package cn.wgn.website.handler;

import cn.wgn.website.dto.ApiRes;
import cn.wgn.website.entity.RolePermissionEntity;
import cn.wgn.website.enums.RedisPrefixKeyEnum;
import cn.wgn.website.mapper.RolePermissionMapper;
import cn.wgn.website.utils.RedisUtil;
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
    private final RedisUtil redisUtil;
    private final RolePermissionMapper rolePermissionMapper;

    @Autowired
    public AuthorizeAspect(RedisUtil redisUtil, RolePermissionMapper rolePermissionMapper) {
        this.redisUtil = redisUtil;
        this.rolePermissionMapper = rolePermissionMapper;
    }

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
        HttpServletRequest request = attributes.getRequest();
        // 获取token
        String token = request.getHeader("token");
        // 获取注解
        String[] codes = authorize.value();

        // 未登录
        if (Strings.isNullOrEmpty(token) || !redisUtil.exists(token, RedisPrefixKeyEnum.Token.toString())) {
            return ApiRes.unAuthorized();
        }

        if (codes.length == 0) {
            // 注解为空,登陆即可
            return pjp.proceed();
        } else {
            String[] arr = redisUtil.get(token, RedisPrefixKeyEnum.Token.toString()).split(":");
            int roleId = Integer.parseInt(arr[2]);
            // roleId 的所有权限
            List<RolePermissionEntity> powers = rolePermissionMapper.selectList(
                    new QueryWrapper<RolePermissionEntity>().lambda()
                            .eq(RolePermissionEntity::getRoleId, roleId)
            );

            for (String code : codes) {
                for (RolePermissionEntity power : powers) {
                    if (code.equals(power.getPermissionId())) {
                        // 身份匹配成功
                        return pjp.proceed();
                    }
                }
            }

            return ApiRes.unAuthorized("权限不足：" + Arrays.toString(codes));
        }
    }

}