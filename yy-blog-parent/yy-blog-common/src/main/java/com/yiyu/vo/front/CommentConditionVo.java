package com.yiyu.vo.front;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yiyu
 * @ClassName : com.yiyu.vo.front.CommentConditionVo
 * @Description :评论列表条件
 * Created by user on 2022-05-16 19:53:34
 * Copyright  2020 user. All rights reserved.
 */
@ApiModel(description = "评论条件")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentConditionVo {
    /**
     * 当前页数
     */
    @ApiModelProperty(name = "pageNum", value = "当前页数", dataType = "Integer")
    private Integer pageNum;
    /**
     * 每页数量
     */
    @ApiModelProperty(name = "pageSize", value = "每页数量", dataType = "Integer")
    private Integer pageSize;
    /**
     * 评论类型对应的id
     */
    @ApiModelProperty(name = "typeId", value = "评论类型对应的id", dataType = "Long")
    private Long typeId;
    /**
     * 评论类型
     */
    @ApiModelProperty(name = "type", value = "评论类型", dataType = "String")
    private String type;
}
