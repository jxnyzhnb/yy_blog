package com.yiyu.utils;

import com.yiyu.entity.LoginUser;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * security工具类
 * @author zh
 * @date 2022/2/23 16:14
 **/
public class SecurityUtils
{

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser()
    {
        Object principal = getAuthentication().getPrincipal();
        //当用户信息已过期
        if (principal instanceof String){
            throw new InsufficientAuthenticationException(HttpCodeEnum.NEED_LOGIN.getMsg());
        }
        return (LoginUser) principal;
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Boolean isAdmin(){
        Long id = getLoginUser().getUser().getId();
        return id != null && 1L == id;
    }

    public static Long getUserId() {
        return getLoginUser().getUser().getId();
    }
    public static Boolean isLogin(){
        Object principal = getAuthentication().getPrincipal();
        return !(principal instanceof String);
    }
}
