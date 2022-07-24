package com.yiyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiyu.constants.UserConst;
import com.yiyu.entity.LoginUser;
import com.yiyu.entity.User;
import com.yiyu.service.BlogLoginService;
import com.yiyu.service.UserService;
import com.yiyu.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.yiyu.vo.front.LoginVo;
import com.yiyu.dto.front.BlogUserLoginDTO;
import com.yiyu.dto.front.UserInfoDTO;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author zh
 * @ClassName : zh.nb.service.impl.LoginServiceImpl
 * @Description :
 * Created by user on 2022-02-21 11:01:54
 * Copyright  2020 user. All rights reserved.
 */
@Service
public class BlogLoginServiceImpl implements BlogLoginService {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Resource
    private HttpServletRequest request;
    @Transactional
    @Override
    public ResponseResult<Object> login(LoginVo user)  {
        UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authentication);
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        User dbUser = loginUser.getUser();
        String id = dbUser.getId().toString();
        //获取登录ip
        String ipAddr = IpUtil.getIpAddr(request);
        String ipSource = IpUtil.getIpSource(ipAddr);
        dbUser.setIpAddr(ipAddr)
                .setIpSource(ipSource);
        loginUser.setUser(dbUser);
        //更新用户ip
        LambdaUpdateWrapper<User> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getIpAddr,ipAddr)
                .set(User::getIpSource,ipSource)
                .eq(User::getId,dbUser.getId());
        userService.update(updateWrapper);
        String token = JwtUtil.createJWT(id);
        redisCache.setCacheObject(UserConst.BLOG_LOGIN_USER_REDIS_PRE+id,loginUser);
        UserInfoDTO userInfos = BeanCopyUtils.copyBean(dbUser, UserInfoDTO.class);
        BlogUserLoginDTO blogUserLogins = new BlogUserLoginDTO(token,userInfos);
        return ResponseResult.okResult(blogUserLogins);
    }

    @Override
    public ResponseResult<Object> logout() {
        //获取登录状态中的用户id
        Long userId = SecurityUtils.getUserId();
        //删除redis中的用户信息
        redisCache.deleteObject(UserConst.BLOG_LOGIN_USER_REDIS_PRE+userId);
        return ResponseResult.okResult();
    }
}
