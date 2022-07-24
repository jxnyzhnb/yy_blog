package com.yiyu.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zh
 * @ClassName : zh.nb.vo.UserInfoVo
 * @Description :
 * Created by user on 2022-02-21 16:37:24
 * Copyright  2020 user. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickName;
    /**
     * 个性签名
     */
    private String signature;

    /**
     * 头像
     */
    private String avatar;

    private String sex;

    private String email;
}
