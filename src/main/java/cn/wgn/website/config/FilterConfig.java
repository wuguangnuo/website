package cn.wgn.website.config;

import cn.wgn.website.handler.TokenAuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 过滤器
 *
 * @author WuGuangNuo
 */
@Configuration
public class FilterConfig {
    /**
     * 需要进行登录验证的接口
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean tokenFilterRegistration() {
        List<String> url = new ArrayList<>();
        url.add("/manage/*");
        url.add("/profile/*");
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new TokenAuthFilter());
        registration.setUrlPatterns(url);
//        registration.addInitParameter("paramName", "paramValue");
        registration.setName("tokenFilter");
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
        return registration;
    }
}
