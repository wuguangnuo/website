package cn.wgn.website.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * SwaggerConfig
 *
 * @author WuGuangNuo
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.wgn.website.controller"))
                .paths(PathSelectors.any())
                .build()
//                .host("api.wuguangnuo.cn:8800") // 发布时取消此处的注释
                .securitySchemes(getSecuritySchemes())
                .securityContexts(getSecuriryContext());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("系统api文档")
                .description("wuguangnuo.cn 主站 API")
//                .termsOfServiceUrl("http://www.wuguangnuo.cn")
                .version("alpha")
                .build();
    }

    private List<ApiKey> getSecuritySchemes() {
        List<ApiKey> result = new ArrayList<>();
        result.add(new ApiKey("Authorization", "token", "header"));
        return result;
    }

    private List<SecurityContext> getSecuriryContext() {
        List<SecurityContext> result = new ArrayList<>();
        SecurityContext context = SecurityContext.builder().securityReferences(getSetcurityReference())
                .forPaths(PathSelectors.any())
                .build();
        result.add(context);
        return result;
    }

    private List<SecurityReference> getSetcurityReference() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        SecurityReference context = new SecurityReference("Authorization", authorizationScopes);
        return Collections.singletonList(context);
    }
}
