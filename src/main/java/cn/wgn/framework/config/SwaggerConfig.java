package cn.wgn.framework.config;

import com.google.common.base.Predicates;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * SwaggerConfig
 *
 * @author WuGuangNuo
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build()
                .securitySchemes(getSecuritySchemes())
                .securityContexts(getSecuriryContext());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("系统API文档")
                .description("wuguangnuo.cn 主站接口")
                .termsOfServiceUrl("https://github.com/wuguangnuo")
                .version("Beta")
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
