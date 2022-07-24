package com.yiyu.controller;

import com.yiyu.annotation.SystemLog;
import com.yiyu.vo.front.LoginVo;
import com.yiyu.exception.SystemException;
import com.yiyu.service.BlogLoginService;
import com.yiyu.service.ThirdLoginService;
import com.yiyu.utils.HttpCodeEnum;
import com.yiyu.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

/**
 * @author zh
 * @ClassName : zh.nb.controller.LoginController
 * @Description :
 * Created by user on 2022-02-21 11:00:56
 * Copyright  2020 user. All rights reserved.
 */
@Api(tags = "前台登录模块")
@RestController
public class BlogLoginController {
    @Autowired
    private BlogLoginService loginService;
    @Autowired
    private ThirdLoginService thirdLoginService;

    @ApiOperation(value = "用户登录")
    @SystemLog(businessName = "用户登录")
    @PostMapping("/login")
    public ResponseResult<Object> login(@RequestBody LoginVo user) {
        //校验用户对象
        if (Objects.isNull(user)) {
            throw new SystemException(HttpCodeEnum.REQUIRE_USER);
        }
        //校验用户名
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(HttpCodeEnum.REQUIRE_USERNAME);
        }
        //校验密码
        if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(HttpCodeEnum.REQUIRE_PASSWORD);
        }
        return loginService.login(user);
    }

    @ApiOperation(value = "用户注销")
    @SystemLog(businessName = "用户注销")
    @PostMapping("/logout")
    public ResponseResult<Object> logout() {
        return loginService.logout();
    }

    @ApiOperation(value = "第三方GitHub登录")
    @SystemLog(businessName = "第三方GitHub登录")
    @PostMapping("/login/github")
    public ResponseResult<Object> githubLogin(@RequestBody Map<String, String> code) {
        return thirdLoginService.githubLogin(code.get("code"));
    }
}
