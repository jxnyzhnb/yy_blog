package com.yiyu.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author zh
 * @ClassName : zh.nb.vo.CommentListVo
 * @Description :
 * Created by user on 2022-02-23 10:32:16
 * Copyright  2020 user. All rights reserved.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
public class CommentListDTO {
    private Long id;
    /**
     * 文章id
     */
    private Long articleId;
    /**
     * 根评论id
     */
    private Long rootId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 点赞人数
     */
    private Integer likeCount;
    /**
     * 当前用户是否点过赞
     */
    private Boolean isLike;
    /**
     * 该条评论回复的评论的userid
     */
    private Long toCommentUserId;
    /**
     * 该条评论回复的评论的userName
     */
    private String toCommentUserName;
    /**
     * 回复的评论id
     */
    private Long toCommentId;

    private Long createBy;

    private Date createTime;

    private String nickName;
    /**
     * 当前评论的用户的头像
     **/
    private String avatar;
    /**
     * 根评论的子评论
     */
    private List<CommentListDTO> children;
}
