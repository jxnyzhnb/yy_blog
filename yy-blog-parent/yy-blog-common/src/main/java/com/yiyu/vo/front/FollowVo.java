package com.yiyu.vo.front;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yiyu
 * @ClassName : com.yiyu.vo.front.FollowVo
 * @Description :
 * Created by user on 2022-05-18 09:47:09
 * Copyright  2020 user. All rights reserved.
 */
@ApiModel(description = "关注")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowVo {
    /**
     * 被关注用户的id
     */
    @ApiModelProperty(name = "toUserId", value = "被关注用户的id", dataType = "Long")
    private Long toUserId;
    /**
     * 文章或帖子id
     */
    @ApiModelProperty(name = "typeId", value = "类型id", dataType = "Long")
    private Long typeId;
    /**
     * 类型(可以是文章或帖子或其他地方，其中文章0，帖子1，其它2)
     */
    @ApiModelProperty(name = "type", value = "类型", dataType = "String")
    private String type;
}
