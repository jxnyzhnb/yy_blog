package com.yiyu.vo.front;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yiyu
 * @ClassName : com.yiyu.vo.front.UserBasicVo
 * @Description :
 * Created by user on 2022-05-18 22:05:24
 * Copyright  2020 user. All rights reserved.
 */
@ApiModel(description = "用户博文数据")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserBasicVo {
    /**
     * 文章id
     */
    @ApiModelProperty(name = "id", value = "文章id", dataType = "Long")
    private Long id;
    /**
     * 来源
     */
    @ApiModelProperty(name = "source", value = "来源", dataType = "String")
    private String source;
    /**
     * 用户id
     */
    @ApiModelProperty(name = "userId", value = "用户id", dataType = "Long")
    private Long userId;
}
