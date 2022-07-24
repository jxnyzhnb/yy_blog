package com.yiyu.dto.front;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author yiyu
 * @ClassName : com.yiyu.dto.front.LikeDTO
 * @Description :
 * Created by user on 2022-05-17 22:26:10
 * Copyright  2020 user. All rights reserved.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
public class LikeDTO {
    /**
     * 用户id
     */
    private Long id;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 点赞数量
     */
    private Integer likeCount;

}
