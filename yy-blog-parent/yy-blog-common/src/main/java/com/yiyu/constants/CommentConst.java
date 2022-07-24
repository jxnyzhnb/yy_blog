package com.yiyu.constants;

import io.swagger.models.auth.In;

/**
 * @author yiyu
 * @ClassName : com.yiyu.constants.CommentConst
 * @Description :
 * Created by user on 2022-05-18 16:45:53
 * Copyright  2020 user. All rights reserved.
 */
public class CommentConst {
    /**
     * 评论为根评论
     */
    public static final Long COMMENT_IS_ROOT = -1L;
    /**
     * 当评论为根评论时,回复评论时的默认userId
     */
    public static final Long TO_COMMENT_USERID_DEFAULT = -1L;
    /**
     * 评论类型为文章
     */
    public static final String ARTICLE_COMMENT_TYPE = "0";
    /**
     * 评论类型为友链
     */
    public static final String DISCUSS_COMMENT_TYPE = "1";

}
