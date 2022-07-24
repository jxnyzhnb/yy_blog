package com.yiyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yiyu.dto.front.UserBasicDTO;
import com.yiyu.entity.User;
import com.yiyu.vo.front.RegisterVo;
import com.yiyu.vo.front.UpdateUserInfoVo;
import com.yiyu.utils.ResponseResult;
import com.yiyu.dto.front.UserInfoDTO;
import com.yiyu.vo.front.UserBasicVo;

import java.text.ParseException;

/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2022-02-14 15:58:00
 */
public interface UserService extends IService<User> {

    ResponseResult<UserInfoDTO> userInfo() ;

    ResponseResult<Object> updateUserInfo(UpdateUserInfoVo user) ;

    ResponseResult<Object> register(RegisterVo registerVo) ;

    ResponseResult<UserBasicDTO> profile(UserBasicVo userBasicVo) throws ParseException;

}

