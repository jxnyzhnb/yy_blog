package com.yiyu.service;

import com.yiyu.dto.back.AdminLoginDTO;
import com.yiyu.utils.ResponseResult;
import com.yiyu.vo.front.LoginVo;

public interface AdminLoginService {
    ResponseResult<AdminLoginDTO> login(LoginVo loginVo);
}
