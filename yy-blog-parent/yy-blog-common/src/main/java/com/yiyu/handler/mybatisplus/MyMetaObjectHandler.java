package com.yiyu.handler.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import com.yiyu.utils.SecurityUtils;

import java.util.Date;
/**
 * mybatisPlus对指定字段进行自动填充控制器
 * @author zh
 * @date 2022/2/23 16:19
 **/
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId;
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            e.printStackTrace();
            userId = -1L;//表示是自己创建
        }
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("createBy",userId , metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        if (SecurityUtils.getAuthentication()==null){
            this.setFieldValByName("updateBy", -1L, metaObject);
            return;
        }
        this.setFieldValByName("updateBy", SecurityUtils.getUserId(), metaObject);
    }
}
