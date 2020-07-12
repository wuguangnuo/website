package cn.wgn.framework.aspectj;

import cn.wgn.framework.utils.RedisUtil;
import cn.wgn.framework.utils.ip.IpUtil;
import cn.wgn.framework.web.entity.VisitorEntity;
import cn.wgn.framework.web.enums.RedisPrefixKeyEnum;
import cn.wgn.framework.web.mapper.VisitorMapper;
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
    private VisitorMapper visitorMapper;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * AOP增强：所有controller包下的公共方法
     */
    @Around(value = "execution(public * cn.wgn..*.controller..* (..))")
    public Object handle(ProceedingJoinPoint pjp) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();


        String token = request.getHeader("token");
        String us = redisUtil.get(token, RedisPrefixKeyEnum.TOKEN.getValue());
//        String serviePath = request.getServletPath();

        VisitorEntity vEntity = new VisitorEntity();
        vEntity.setLk(request.getServletPath())
                .setIp(IpUtil.getIp(request))
                .setAg(request.getHeader("User-Agent"))
                .setTm(LocalDateTime.now())
                .setUs(us);
        visitorMapper.insert(vEntity);

        return pjp.proceed();

    }
}
