package com.yiyu.vo.front;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zh
 * @ClassName : zh.nb.dto.UpdateUserInfoDTO
 * @Description :
 * Created by user on 2022-03-06 09:59:11
 * Copyright  2020 user. All rights reserved.
 */
@ApiModel(description = "更新用户信息")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserInfoVo {
    /**
     * 用户id
     */
    @ApiModelProperty(name = "id", value = "用户id", dataType = "Long")
    private Long id;
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
     * 性别(0男,1女,2未知)
     */
    @ApiModelProperty(name = "sex", value = "性别(0男,1女,2未知)", dataType = "String")
    private String sex;
    /**
     * 头像
     */
    @ApiModelProperty(name = "avatar", value = "头像", dataType = "String")
    private String avatar;
}
