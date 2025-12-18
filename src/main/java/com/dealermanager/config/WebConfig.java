package com.dealermanager.config;

import com.dealermanager.interceptor.LoginHandlerInterceptor;
import com.google.common.collect.Lists;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;


/**
 * @author
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginHandlerInterceptor loginHandlerInterceptor;

    @Value("${swagger.show}")
    private boolean swaggerShow;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket commonDocket() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("外贸小程序")
                .version("1.0.0")
                .description("后台接口")
                .build();

        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name("x-auth-token").description("token").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        List<Parameter> aParameters = Lists.newArrayList();
        aParameters.add(aParameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerShow)
                .apiInfo(apiInfo)
                .globalOperationParameters(aParameters)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dealermanager"))
                .paths(PathSelectors.any())
                .build();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> swaggerPathPatterns = Lists.newArrayList("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
        registry.addInterceptor(loginHandlerInterceptor).excludePathPatterns(swaggerPathPatterns);
    }
}
