package com.yiyu;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * @author zh
 * @ClassName : zh.nb.YyBlogCommonApplication
 * @Description :
 * Created by user on 2022-02-14 15:34:23
 * Copyright  2020 user. All rights reserved.
 */
@SpringBootApplication
@MapperScan("com.yiyu.mapper")
public class YyBlogCommonApplication {
    @PostConstruct
    public void init(){
        //解决Netty启动冲突问题
        //看 Netty4Utils.setAvailableProcessors()
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }
    public static void main(String[] args) {
        SpringApplication.run(YyBlogCommonApplication.class,args);
    }
}
