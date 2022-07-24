package com.yiyu.vo.front;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author zh
 * @ClassName : zh.nb.dto.LoginDTO
 * @Description :
 * Created by user on 2022-03-06 09:46:16
 * Copyright  2020 user. All rights reserved.
 */
@ApiModel(description = "登录")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVo {
    /**
     * 用户名
     */
    @NotNull
    @ApiModelProperty(name = "userName", value = "用户名", dataType = "String")
    private String userName;
    /**
     * 密码
     */
    @NotNull
    @ApiModelProperty(name = "password", value = "密码", dataType = "String")
    private String password;
}
