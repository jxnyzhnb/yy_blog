package com.yiyu.controller;

import com.yiyu.annotation.SystemLog;
import com.yiyu.dto.front.UserBasicDTO;
import com.yiyu.dto.front.UserInfoDTO;
import com.yiyu.service.UserService;
import com.yiyu.utils.ResponseResult;
import com.yiyu.vo.front.RegisterVo;
import com.yiyu.vo.front.UpdateUserInfoVo;
import com.yiyu.vo.front.UserBasicVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

/**
 * 用户表(User)表控制层
 *
 * @author makejava
 * @since 2022-02-14 16:15:10
 */
@Api(tags = "用户模块")
@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * 服务对象
     */
    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取用户信息")
    @SystemLog(businessName = "获取用户信息")
    @GetMapping("/userInfo")
    public ResponseResult<UserInfoDTO> userInfo() {
        return userService.userInfo();
    }

    @ApiOperation(value = "修改用户信息")
    @SystemLog(businessName = "修改用户信息")
    @PutMapping("/userInfo")
    public ResponseResult<Object> updateUserInfo(@RequestBody UpdateUserInfoVo userInfoVo) {
        return userService.updateUserInfo(userInfoVo);
    }

    @ApiOperation(value = "用户注册")
    @SystemLog(businessName = "用户注册")
    @PostMapping("/register")
    public ResponseResult<Object> register(@RequestBody RegisterVo registerVo) {
        return userService.register(registerVo);
    }
    @ApiOperation(value = "用户博文数据")
    @SystemLog(businessName = "用户博文数据")
    @GetMapping("/profile")
    public ResponseResult<UserBasicDTO> profile(UserBasicVo userBasicVo) throws ParseException {
        return userService.profile(userBasicVo);
    }
}

