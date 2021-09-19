package cn.wgn.framework.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.bstek.urule.console.servlet.URuleServlet;

import javax.servlet.http.HttpServlet;

@Component
public class URuleServletRegistration {
    @Bean
    public ServletRegistrationBean<HttpServlet> registerURuleServlet() {
        return new ServletRegistrationBean(new URuleServlet(), "/urule/*");
    }
}