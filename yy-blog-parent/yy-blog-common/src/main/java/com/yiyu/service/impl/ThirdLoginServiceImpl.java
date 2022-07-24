package com.yiyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yiyu.constants.UserConst;
import com.yiyu.entity.LoginUser;
import com.yiyu.entity.User;
import com.yiyu.service.ThirdLoginService;
import com.yiyu.service.UserService;
import com.yiyu.utils.*;
import com.yiyu.dto.front.BlogUserLoginDTO;
import com.yiyu.dto.front.UserInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zh
 * @ClassName : com.yiyu.service.impl.ThirdLoginServiceImpl
 * @Description :
 * Created by user on 2022-05-15 09:23:29
 * Copyright  2020 user. All rights reserved.
 */
@Slf4j
@Service
public class ThirdLoginServiceImpl implements ThirdLoginService {

    @Value("${third.github.client_id}")
    private String GITHUB_CLIENT_ID;

    @Value("${third.github.client_secret}")
    private String GITHUB_CLIENT_SECRET;

    @Value("${third.github.grant_type}")
    private String GITHUB_GRANT_TYPE;

    @Value("${third.github.redirect_uri}")
    private String GITHUB_REDIRECT_URI;

    @Value("${third.github.access_token_url}")
    private String GITHUB_ACCESS_TOKEN_URL;

    @Value("${third.github.user_info_url}")
    private String GITHUB_USER_INFO_URL;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisCache redisCache;
    @Resource
    private HttpServletRequest request;

    @Transactional
    @Override
    public ResponseResult<Object> githubLogin( String code)  {
        //已获取授权码code
        log.info("code值是"+code);
        Map<String, String> map = new HashMap<>();
        map.put("client_id", GITHUB_CLIENT_ID);
        map.put("client_secret", GITHUB_CLIENT_SECRET);
        map.put("grant_type", GITHUB_GRANT_TYPE);
        map.put("code", code);
        map.put("redirect_uri", GITHUB_REDIRECT_URI);
        log.info(GITHUB_REDIRECT_URI);
        //通过code和诸多配置参数请求获得access_token
        Map<String, String> resp = restTemplate.postForObject(GITHUB_ACCESS_TOKEN_URL, map, Map.class);
        log.info("fssdsd{}", resp);
        HttpHeaders httpheaders = new HttpHeaders();
        httpheaders.add("Authorization", "token " + resp.get("access_token"));
        HttpEntity<?> httpEntity = new HttpEntity<>(httpheaders);
        ResponseEntity<Map> exchange = restTemplate.exchange(GITHUB_USER_INFO_URL, HttpMethod.GET, httpEntity, Map.class);

        Map user = exchange.getBody();
        //根据id查询数据库是否有该用户第三方登录的信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,user.get("id").toString());
        User dbUser = userService.getOne(queryWrapper);
        //获得用户ip
        //获取登录ip
        String ipAddr = IpUtil.getIpAddr(request);
        String ipSource = IpUtil.getIpSource(ipAddr);
        //更新用户ip

        //当前用户在数据库中没有信息
        if (Objects.isNull(dbUser)) {
            //保存用户信息
            dbUser = new User();
            dbUser.setUserName( user.get("id").toString())
                    .setAvatar((String) user.get("avatar_url"))
                    .setNickName((String) user.get("login"))
                    .setIpAddr(ipAddr)
                    .setIpSource(ipSource);
            userService.save(dbUser);
            //更新ip地址
        }else {
            LambdaUpdateWrapper<User> updateWrapper=new LambdaUpdateWrapper<>();
            updateWrapper.set(User::getIpAddr,ipAddr)
                    .set(User::getIpSource,ipSource)
                    .eq(User::getId,dbUser.getId());
            userService.update(updateWrapper);
        }
        String id = dbUser.getId().toString();
        String token = JwtUtil.createJWT(id);
        LoginUser loginUser = new LoginUser();
        loginUser.setUser(dbUser)
                .setPermissions(new ArrayList<>());
        redisCache.setCacheObject(UserConst.BLOG_LOGIN_USER_REDIS_PRE + dbUser.getId(), loginUser);
        UserInfoDTO userInfos = BeanCopyUtils.copyBean(dbUser, UserInfoDTO.class);
        try {
            System.out.println("exchange.getBody() = " + new ObjectMapper().writeValueAsString(exchange.getBody()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        BlogUserLoginDTO blogUserLogins = new BlogUserLoginDTO(token, userInfos);
        return ResponseResult.okResult(blogUserLogins);
    }
}
