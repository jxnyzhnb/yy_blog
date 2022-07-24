package com.yiyu.service;

import com.yiyu.vo.front.LoginVo;
import com.yiyu.utils.ResponseResult;

/**
 * @author zh
 * @ClassName : zh.nb.service.LoginService
 * @Description :
 * Created by user on 2022-02-21 11:01:38
 * Copyright  2020 user. All rights reserved.
 */
public interface BlogLoginService {
    ResponseResult<Object> login(LoginVo user) ;

    ResponseResult<Object> logout();
}
