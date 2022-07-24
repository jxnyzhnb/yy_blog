package com.yiyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiyu.entity.LoginUser;
import com.yiyu.entity.User;
import com.yiyu.mapper.MenuMapper;
import com.yiyu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author zh
 * @ClassName : zh.nb.service.impl.UserDetailServiceImpl
 * @Description :
 * Created by user on 2022-02-21 15:59:28
 * Copyright  2020 user. All rights reserved.
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User:: getUserName,username);
        User user = userMapper.selectOne(queryWrapper);
        //用户名不存在
        if (Objects.isNull(user)){
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<String> permission = menuMapper.selectPermsByUserId(user.getId());
        return new LoginUser(user,permission);
    }
}
