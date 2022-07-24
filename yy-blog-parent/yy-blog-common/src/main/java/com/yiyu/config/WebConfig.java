package com.yiyu.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author zh
 * @ClassName : zh.nb.securityToken.config.CorsConfig
 * @Description :跨域配置类
 * Created by user on 2021-12-27 20:17:51
 * Copyright  2020 user. All rights reserved.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
        registry.addMapping("/**")
                //设置允许请求跨域的域名
                .allowedOriginPatterns("*")
                //设置允许的请求方式
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                //设置是否使用cookie
                .allowCredentials(true)
                //设置允许的请求头
                .allowedHeaders("*")
                //跨域的允许时间
                .maxAge(3600);
    }
    /**
     * 使用@Bean注入fastJsonHttpMessageConvert
     * @author zh
     * @date 2022/2/19 17:21
     * @return org.springframework.http.converter.HttpMessageConverter<?> :
     **/
    @Bean
    public HttpMessageConverter<?> fastJsonHttpMessageConverters() {
        //1.需要定义一个Convert转换消息的对象,覆盖掉Jackson的消息转换器
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        //配置json数据的Date时间格式
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");

        SerializeConfig.globalInstance.put(Long.class, ToStringSerializer.instance);

        fastJsonConfig.setSerializeConfig(SerializeConfig.globalInstance);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        return fastConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverters());
    }
}
