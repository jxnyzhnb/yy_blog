package com.yiyu.vo.front;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yiyu
 * @ClassName : com.yiyu.vo.front.LikeVo
 * @Description :点赞/取消点赞
 * Created by user on 2022-05-16 20:04:20
 * Copyright  2020 user. All rights reserved.
 */
@ApiModel(description = "赞")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LikeVo {
    /**
     * 文章id/评论id
     */
    @ApiModelProperty(name = "id", value = "文章id/评论id", dataType = "Long")
    private Long typeId;
    /**
     * 文章类型(0)/评论类型(1)/帖子2
     */
    @ApiModelProperty(name = "type", value = "文章类型(0)/评论类型(1)", dataType = "Integer")
    private Integer type;
}
