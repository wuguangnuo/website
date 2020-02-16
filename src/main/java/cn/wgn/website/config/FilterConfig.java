//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 过滤器
// *
// * @author WuGuangNuo
// */
//@Configuration
//public class FilterConfig {
//    @Bean
//    public FilterRegistrationBean tokenFilterRegistration() {
//        List<String> url = new ArrayList<>();
//        url.add("/operate/*");
//        url.add("/channel/*");
//        url.add("/flocking/*");
//        url.add("/sticker/*");
//        url.add("/users/*");
//        url.add("/activity/*");
//        url.add("/profile/*");
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new TokenAuthFilter());
//        registration.setUrlPatterns(url);
////        registration.addInitParameter("paramName", "paramValue");
//        registration.setName("tokenFilter");
//        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
//        return registration;
//    }
//}
