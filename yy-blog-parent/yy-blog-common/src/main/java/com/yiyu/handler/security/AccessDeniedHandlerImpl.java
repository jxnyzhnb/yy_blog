package com.yiyu.handler.security;

import com.alibaba.fastjson.JSON;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
 * @ClassName : zh.nb.securityToken.handler.AccessDeniedHandlerImpl
 * @Description :权限不足异常处理器(当用户出现权限不足的情况,响应的对象自定义为ResponseResult对象)
 * Created by user on 2021-12-27 19:56:21
 * Copyright  2020 user. All rights reserved.
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        accessDeniedException.printStackTrace();
        ResponseResult<Object> result = ResponseResult.errorResult(HttpCodeEnum.NO_OPERATOR_AUTH);
        //处理异常
        WebUtils.renderString(response,JSON.toJSONString(result));
    }
}
