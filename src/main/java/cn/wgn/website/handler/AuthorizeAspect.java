//package cn.wgn.website.handler;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.google.common.base.Strings;
//import com.xyj.bisys.dto.ApiRes;
//import com.xyj.bisys.entity.RolePermission;
//import com.xyj.bisys.enums.RedisPrefixKeyEnum;
//import com.xyj.bisys.mapper.RolePermissionMapper;
//import com.xyj.bisys.utils.RedisUtil;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * Spring Aop(aspect)
// * AOP 自定义注解权限控制
// *
// * @author WuGuangNuo
// */
//@Aspect
//@Component
//public class AuthorizeAspect {
//    private final RedisUtil redisUtil;
//    private final RolePermissionMapper rolePermissionMapper;
//
//    @Autowired
//    public AuthorizeAspect(RedisUtil redisUtil, RolePermissionMapper rolePermissionMapper) {
//        this.redisUtil = redisUtil;
//        this.rolePermissionMapper = rolePermissionMapper;
//    }
//
//    /**
//     * Spring中使用@Pointcut注解来定义方法切入点
//     *
//     * @Pointcut 用来定义切点，针对方法
//     * @Aspect 用来定义切面，针对类
//     * 后面的增强均是围绕此切入点来完成的
//     */
//    @Pointcut("@annotation(authorize)")
//    public void doAuthorize(Authorize authorize) {
//    }
//
//    @Around(value = "doAuthorize(authorize)", argNames = "pjp,authorize")
//    public Object deBefore(ProceedingJoinPoint pjp, Authorize authorize) throws Throwable {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        // 获取token
//        String token = request.getHeader("token");
//        // 获取注解
//        String[] codes = authorize.value();
//
//        // 未登录
//        if (Strings.isNullOrEmpty(token) || !redisUtil.exists(token, RedisPrefixKeyEnum.Sys.toString()))
//            return ApiRes.unAuthorized();
//
//        if (codes.length == 0) {
//            // 注解为空,登陆即可
//            return pjp.proceed();
//        } else {
//            String[] arr = redisUtil.get(token, RedisPrefixKeyEnum.Sys.toString()).split(":");
//            int roleId = Integer.valueOf(arr[2]);
//            // roleId 的所有权限
//            List<RolePermission> powers = rolePermissionMapper.selectList(
//                    new QueryWrapper<RolePermission>().lambda()
//                            .eq(RolePermission::getRoleId, roleId)
//            );
//
//            for (String code : codes) {
//                for (RolePermission power : powers) {
//                    if (code.equals(power.getPermissionCode())) {
//                        // 身份匹配成功
//                        return pjp.proceed();
//                    }
//                }
//            }
//
//            return ApiRes.unAuthorized("权限不足：" + Arrays.toString(codes));
//        }
//    }
//
//}