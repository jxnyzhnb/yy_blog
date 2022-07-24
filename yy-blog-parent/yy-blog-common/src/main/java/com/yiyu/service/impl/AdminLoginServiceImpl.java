package com.yiyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiyu.constants.UserConst;
import com.yiyu.dto.back.AdminInfoDTO;
import com.yiyu.dto.back.AdminLoginDTO;
import com.yiyu.entity.LoginUser;
import com.yiyu.entity.User;
import com.yiyu.service.AdminLoginService;
import com.yiyu.service.UserService;
import com.yiyu.utils.*;
import com.yiyu.vo.front.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author yiyu
 * @version 1.0 2022-06-01 15:51:06
 * @ClassName : com.yiyu.service.impl.AdminLoginServiceImpl
 * @Description :
 */
@Service
public class AdminLoginServiceImpl implements AdminLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Resource
    private HttpServletRequest request;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult<AdminLoginDTO> login(LoginVo loginVo) {
        //进行认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginVo.getUserName(), loginVo.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //获得认证信息
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
        redisCache.setCacheObject(UserConst.ADMIN_LOGIN_USER_REDIS_PRE+id,loginUser);
        AdminInfoDTO userInfos = BeanCopyUtils.copyBean(dbUser, AdminInfoDTO.class);
        AdminLoginDTO adminLogins = new AdminLoginDTO(token,userInfos);
        return ResponseResult.okResult(adminLogins);
    }
}
