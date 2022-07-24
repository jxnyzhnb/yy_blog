package com.yiyu.controller;

import com.yiyu.dto.back.AdminLoginDTO;
import com.yiyu.service.AdminLoginService;
import com.yiyu.utils.ResponseResult;
import com.yiyu.vo.front.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yiyu
 * @version 1.0 2022-05-31 22:08:57
 * @ClassName : com.yiyu.controller.AdminLoginController
 * @Description :
 */
@RestController
public class AdminLoginController {
    @Autowired
    private AdminLoginService adminLoginService;

    @PostMapping("/login")
    public ResponseResult<AdminLoginDTO> login(@RequestBody LoginVo loginVo) {
        return adminLoginService.login(loginVo);
    }
}
