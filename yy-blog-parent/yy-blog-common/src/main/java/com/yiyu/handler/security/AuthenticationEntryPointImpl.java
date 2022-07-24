package com.yiyu.handler.security;

import com.alibaba.fastjson.JSON;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.yiyu.utils.HttpCodeEnum;
import com.yiyu.utils.ResponseResult;
import com.yiyu.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zh
 * @ClassName : zh.nb.securityToken.handler.AuthenticationEntryPointImpl
 * @Description :认证失败异常处理器(当用户出现认证失败的情况,响应的对象自定义为ResponseResult对象)
 * Created by user on 2021-12-27 19:35:51
 * Copyright  2020 user. All rights reserved.
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        authException.printStackTrace();
        ResponseResult<Object> result;
        //出现用户名或密码错误或账户被锁定的情况,响应相对应的信息
        if (authException instanceof BadCredentialsException){
            result= ResponseResult.errorResult(HttpCodeEnum.LOGIN_ERROR.getCode(),authException.getMessage());
            //出现未登录的情况
        }else if (authException instanceof InsufficientAuthenticationException){
            result=ResponseResult.errorResult(HttpCodeEnum.NEED_LOGIN);
            //出现其他情况
        }else {
            result=ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR,"认证或授权失败");
        }
        //处理异常
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
