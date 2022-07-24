package com.yiyu.handler.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.yiyu.exception.SystemException;
import com.yiyu.utils.HttpCodeEnum;
import com.yiyu.utils.ResponseResult;

import java.util.Objects;

/**
 * @author zh
 * @ClassName : zh.nb.handler.exception.GlobalExceptionHandler
 * @Description :全局异常统一处理控制器
 * Created by user on 2022-02-22 20:00:31
 * Copyright  2020 user. All rights reserved.
 */
//@ControllerAdvice
//@ResponseBody

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理SystemException异常
     *
     * @param e:
     * @return zh.nb.utils.ResponseResult<java.lang.Object> :
     * @author zh
     * @date 2022/2/22 20:12
     **/
    @ExceptionHandler(SystemException.class)
    public ResponseResult<Object> systemExceptionHandler(SystemException e) {
        //打印异常信息
        log.error("出现了SystemException异常: ", e);
        //封装异常信息给前端
        return ResponseResult.errorResult(e.getCode(), e.getMsg());
    }

    /**
     * AuthenticationException认证失败异常
     *  注:由于定义了全局异常处理器导致spring security的
     *  AuthenticationEntryPoint认证失败处理器中要处理的BadCredentialsException会不会被该处理器捕获，
     *  如果不在全局异常处理器中额外定义AuthenticationException处理器
     *  BadCredentialsException异常就会被Exception异常处理方法捕获到
     * @param authException:
     * @return zh.nb.utils.ResponseResult<java.lang.Object> :
     * @author zh
     * @date 2022/2/22 20:12
     **/
    @ExceptionHandler(AuthenticationException.class)
    public ResponseResult<Object> authenticationExceptionHandler(AuthenticationException authException) {
        //打印异常信息
        log.error("出现了AuthenticationException认证失败异常: ", authException);
        //出现用户名或密码错误或账户被锁定的情况,响应相对应的信息
        if (authException instanceof BadCredentialsException) {
            return ResponseResult.errorResult(HttpCodeEnum.LOGIN_ERROR.getCode(), authException.getMessage());
            //出现未登录的情况
        } else if (authException instanceof InsufficientAuthenticationException) {
            return ResponseResult.errorResult(HttpCodeEnum.NEED_LOGIN);
            //出现其他情况
        } else {
            return ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR, "认证或授权失败");
        }
    }

    /**
     * AccessDeniedException权限不足异常
     *
     * @param authException:
     * @return zh.nb.utils.ResponseResult<java.lang.Object> :
     * @author zh
     * @date 2022/2/22 20:12
     **/
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseResult<Object> accessDeniedExceptionHandler(AccessDeniedException authException) {
        //打印异常信息
        log.error("出现了AccessDeniedException权限不足异常: ", authException);
        return ResponseResult.errorResult(HttpCodeEnum.NO_OPERATOR_AUTH);
    }
    /**
     * 空指针异常
     *
     * @param nullPointerException:
     * @return zh.nb.utils.ResponseResult<java.lang.Object> :
     * @author zh
     * @date 2022/2/22 20:12
     **/
    @ExceptionHandler(NullPointerException.class)
    public ResponseResult<Object> nullPointerExceptionHandler(NullPointerException nullPointerException) {
        //打印异常信息
        log.error("出现了空指针异常: ", nullPointerException);
        //出现就直接报404异常
        return ResponseResult.errorResult(HttpCodeEnum.NOT_FOUND);
    }

    /**
     * 处理Exception异常
     *
     * @param e: e
     * @return zh.nb.utils.ResponseResult<java.lang.Object> :
     * @author zh
     * @date 2022/2/22 20:12
     **/
    @ExceptionHandler(Exception.class)
    public ResponseResult<Object> exceptionHandler(Exception e) {
        //打印异常信息
        log.error("出现了Exception异常:", e);
        //封装异常信息给前端
        return ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR);
    }
}
