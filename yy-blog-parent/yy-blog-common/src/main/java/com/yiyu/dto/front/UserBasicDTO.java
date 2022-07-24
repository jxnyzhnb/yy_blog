package com.yiyu.dto.front;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author yiyu
 * @ClassName : com.yiyu.dto.front.UserBasicDTO
 * @Description :
 * Created by user on 2022-05-18 22:09:41
 * Copyright  2020 user. All rights reserved.
 */
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserBasicDTO {
    private Long id;
    private String nickName;
    private String avatar;
    private String codeAge;
    private Long articleCount;
    private Long viewCount;
    private Integer followCount;
    private Integer likeCount;
    private Integer commentCount;
    private Boolean self;
    private Boolean follow;



}
