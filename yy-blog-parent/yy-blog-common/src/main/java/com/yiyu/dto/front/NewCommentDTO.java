package com.yiyu.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author yiyu
 * @ClassName : com.yiyu.dto.front.NewCommentDTO
 * @Description :
 * Created by user on 2022-05-22 16:09:35
 * Copyright  2020 user. All rights reserved.
 */
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewCommentDTO {
    /**
     * 类型id
     */
    private Long typeId;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 评论的用户id
     */
    private Long createBy;
    /**
     * 评论昵称
     */
    private String nickName;
    /**
     * 评论内容
     */
    private String content;
}
