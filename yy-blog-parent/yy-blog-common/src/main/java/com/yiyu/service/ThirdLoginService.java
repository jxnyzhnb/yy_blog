package com.yiyu.service;

import com.yiyu.utils.ResponseResult;

public interface ThirdLoginService {
    ResponseResult<Object> githubLogin(String code) ;
}
