package com.yiyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zh
 * @ClassName : zh.nb.YyBlogApplication
 * @Description :
 * Created by user on 2022-02-14 14:51:07
 * Copyright  2020 user. All rights reserved.
 */

@SpringBootApplication
@EnableScheduling //开启定时任务
public class YyBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(YyBlogApplication.class,args);
    }
}
