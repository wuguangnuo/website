package cn.wgn.framework.aspectj;

import cn.wgn.framework.utils.TokenUtil;
import cn.wgn.framework.utils.ip.IpUtil;
import cn.wgn.framework.web.entity.VisitorEntity;
import cn.wgn.framework.web.service.IVisitorService;
import io.jsonwebtoken.JwtException;
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
 * 控制层切面
 *
 * @author WuGuangNuo
 * @date Created in 2020/3/6 10:06
 */

@Order(1)
@Aspect
@Component
public class ControllerAspect {
    @Autowired
    private IVisitorService visitorService;

    /**
     * AOP增强：所有controller包下的公共方法
     * 用以统计接口调用情况
     */
    @Around(value = "execution(public * cn.wgn..*.controller..* (..))")
    public Object handle(ProceedingJoinPoint pjp) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // jwt 非标准格式认为未登录
        String us;
        Long id = TokenUtil.getUserId();
        if (id == null) {
            us = null;
        } else {
            us = id + ":" + TokenUtil.getUserName();
        }

        VisitorEntity vEntity = new VisitorEntity();
        vEntity.setLk(request.getServletPath());
        vEntity.setIp(IpUtil.getIp(request));
        vEntity.setAg(request.getHeader("User-Agent"));
        vEntity.setTm(LocalDateTime.now());
        vEntity.setUs(us);
        visitorService.save(vEntity);

        return pjp.proceed();
    }
}
