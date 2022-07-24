package com.yiyu.vo.front;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zh
 * @ClassName : zh.nb.dto.AddCommentDTO
 * @Description :
 * Created by user on 2022-03-06 09:53:31
 * Copyright  2020 user. All rights reserved.
 */
@ApiModel(description = "评论")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCommentVo {
    /**
     * 评论的帖子或文章id
     */
    @ApiModelProperty(name = "typeId", value = "评论的帖子或文章id", dataType = "Long")
    private Long typeId;
    /**
     * 文章内容
     */
    @ApiModelProperty(name = "content", value = "文章内容", dataType = "String")
    private String content;
    /**
     * 评论根id
     */
    @ApiModelProperty(name = "rootId", value = "评论根id", dataType = "Long")
    private Long rootId;
    /**
     * 回复的评论id
     */
    @ApiModelProperty(name = "toCommentId", value = "回复的评论id", dataType = "Long")
    private Long toCommentId;
    /**
     * 回复的评论的评论人id
     */
    @ApiModelProperty(name = "toCommentUserId", value = "回复的评论的评论人id", dataType = "Long")
    private Long toCommentUserId;
    /**
     * 评论类型
     */
    @ApiModelProperty(name = "type", value = "评论类型", dataType = "String")
    private String type;
}
