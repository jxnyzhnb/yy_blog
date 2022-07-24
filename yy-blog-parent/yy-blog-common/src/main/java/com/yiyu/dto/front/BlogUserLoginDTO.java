package com.yiyu.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zh
 * @ClassName : zh.nb.vo.BlogUserLoginVo
 * @Description :
 * Created by user on 2022-02-21 16:36:27
 * Copyright  2020 user. All rights reserved.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogUserLoginDTO {
    private String token;
    private UserInfoDTO userInfo;
}
