package com.yiyu.aspect;

import com.alibaba.fastjson.JSON;
import com.yiyu.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author zh
 * @ClassName : zh.nb.aspect.LogAspect
 * @Description :
 * Created by user on 2022-03-14 19:04:22
 * Copyright  2020 user. All rights reserved.
 */
@Slf4j
@Component
@Aspect
public class LogAspect {
     @Pointcut("@annotation(com.yiyu.annotation.SystemLog)")
    public void pointcut() {

    }
    @Around("pointcut()")
     public Object printLog(ProceedingJoinPoint pjp) throws Throwable {
         Object result = null;


         try {
              //目标方法执行之前的打印日志操作
              handleBefore(pjp);
              //将目标方法的返回值进行返回,不然目标方法就没有返回值了
              result = pjp.proceed();

         } finally {
              //目标方法执行完后的打印日志操作
              handleAfter(result);
         }

         return result;
    }

     private void handleAfter(Object result) {
         log.info("Response           : {}",JSON.toJSONString(result));
         log.info("===============end=================");
     }
     //获得且切点方法上的SystemLog注解对象
     private SystemLog getSystemLog(ProceedingJoinPoint pjp) {
          //获得该切点方法的签名(即该签名对象中包含完全限定名)
          MethodSignature signature = (MethodSignature) pjp.getSignature();
          Method method = signature.getMethod();
          //获得该方法上的注解对象
          return method.getAnnotation(SystemLog.class);
     }

     private void handleBefore(ProceedingJoinPoint pjp) {
          //spring中**holder一般都是spring封装使用ThreadLocal进行共享资源
          ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
          assert requestAttributes != null;
          HttpServletRequest request = requestAttributes.getRequest();
          SystemLog systemLog=getSystemLog(pjp);
          log.info("==============start================");
          //打印请求URL
          log.info("URL                : {}",request.getRequestURL());
          //打印方法描述信息
          log.info("BusinessName       : {}",systemLog.businessName());
          //打印HTTP请求方法
          log.info("HTTP method        : {}",request.getMethod());
          //打印调用controller的全路径和以及之执行方法
          log.info("Class Method       : {}.{}",pjp.getSignature().getDeclaringTypeName(),pjp.getSignature().getName());
          //打印请求IP地址
          log.info("请求IP              : {}",request.getRemoteHost());
          //打印请求入参
          log.info("Request args       : {}", JSON.toJSONString(pjp.getArgs()));
          log.info("Request -       : {}",    request.getServletPath());

     }
}
