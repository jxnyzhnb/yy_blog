package com.yiyu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * @author yiyu
 * @ClassName : zh.nb.config.SwaggerAutoConfiguration
 * @Description :Swagger的接口文档自动配置类
 * Created by user on 2021-11-05 19:10:47
 * Copyright  2020 user. All rights reserved.
 */
@Configuration
@EnableSwagger2
public class SwaggerAutoConfiguration {
    /**
     * 接口文档配置
     * @author zh
     * @date 2021/11/5 19:29
     * @return springfox.documentation.spring.web.plugins.Docket :
     **/
    @Bean
    public Docket swaggerApi(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo())
                .protocols(Collections.singleton("https"))
                .host("www.yiyu.com")
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yiyu.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    /**
     * 接口文档的基本信息配置
     * @author zh
     * @date 2021/11/5 19:30
     * @return springfox.documentation.service.ApiInfo :
     **/
    public ApiInfo getApiInfo(){
        return  new ApiInfoBuilder().title("博客api文档")
                    .contact(new Contact("yiyu","www.yiyu.com","12306@qq.com"))
                    .version("1.0").description("springboot+vue开发的博客项目").build();
    }
}
