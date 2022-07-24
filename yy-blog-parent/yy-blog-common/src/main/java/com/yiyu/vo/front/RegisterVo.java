package com.yiyu.vo.front;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zh
 * @ClassName : zh.nb.dto.RegisterDTO
 * @Description :
 * Created by user on 2022-03-14 15:59:00
 * Copyright  2020 user. All rights reserved.
 */
@ApiModel(description = "注册")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterVo {
    /**
     * 邮箱
     */
    @ApiModelProperty(name = "email", value = "邮箱", dataType = "String")
    private String email;
    /**
     * 昵称
     */
    @ApiModelProperty(name = "nickName", value = "昵称", dataType = "String")
    private String nickName;
    /**
     * 密码
     */
    @ApiModelProperty(name = "password", value = "密码", dataType = "String")
    private String password;
    /**
     * 用户名
     */
    @ApiModelProperty(name = "userName", value = "用户名", dataType = "String")
    private String userName;
}
