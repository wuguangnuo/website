package cn.wgn.website.handler;

import com.bstek.urule.console.servlet.URuleServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author WuGuangNuo
 * @date Created in 2020/3/25 17:38
 */
@Component
public class UruleServletRegistration {
    @Bean
    public ServletRegistrationBean registerURuleServlet() {
        return new ServletRegistrationBean(new URuleServlet(), "/urule/*");
    }
}