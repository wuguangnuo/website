package cn.wgn.website.handler;

import cn.wgn.website.entity.CallEntity;
import cn.wgn.website.enums.RedisPrefixKeyEnum;
import cn.wgn.website.mapper.CallMapper;
import cn.wgn.website.utils.IpUtil;
import cn.wgn.website.utils.RedisUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 控制层切面，用以统计接口调用情况
 *
 * @author WuGuangNuo
 * @date Created in 2020/3/6 10:06
 */

@Order(1)
@Aspect
@Component
public class ControllerAspect {
    @Autowired
    private IpUtil ipUtil;
    @Autowired
    private CallMapper callMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Around(value = "execution(* cn.wgn.website.controller..*(..))")
    public Object handle(ProceedingJoinPoint pjp) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();


        String token = request.getHeader("token");
        String us = redisUtil.get(token, RedisPrefixKeyEnum.Token.toString());
//        String serviePath = request.getServletPath();

        CallEntity vEntity = new CallEntity();
        vEntity.setLk(request.getServletPath())
                .setIp(ipUtil.getIp(request))
                .setAg(request.getHeader("User-Agent"))
                .setTm(LocalDateTime.now())
                .setUs(us);
        callMapper.insert(vEntity);

        return pjp.proceed();

    }
}
