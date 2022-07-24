package com.yiyu.constants;

/**
 * @author yiyu
 * @ClassName : com.yiyu.constants.NoticeConst
 * @Description :
 * Created by user on 2022-05-18 16:47:16
 * Copyright  2020 user. All rights reserved.
 */
public class NoticeConst {
    /**
     * 评论类型
     */
    public static final String TYPE_COMMENT="comment";
    /**
     * 点赞类型
     */
    public static final String TYPE_LIKE="like";
    /**
     * 通知类型
     */
    public static final String TYPE_NOTICE="notice";
    /**
     * 私信类型
     */
    public static final String TYPE_LETTER="letter";
    /**
     * 系统通知主题
     */
    public static final String TOPIC_SYSTEM_NOTICE = "system_notice" ;
    /**
     * 消费评论博文主题
     */
    public static final String TOPIC_COMMENT_ARTICLE = "comment_article" ;
    /**
     * 消费评论帖子主题
     */
    public static final String TOPIC_COMMENT_DISCUSS = "comment_discuss" ;
    /**
     * 消费回复评论博文主题
     */
    public static final String TOPIC_REPLY_ARTICLE_COMMENT = "reply_article_comment" ;
    /**
     * 消费回复评论贴子主题
     */
    public static final String TOPIC_REPLY_DICUSS_COMMENT = "reply_discuss_comment" ;
    /**
     * 消费点赞文章评论主题
     */
    public static final String TOPIC_LIKE_ARTICLE_COMMENT = "like_article_comment" ;
    /**
     * 消费点赞帖子评论主题
     */
    public static final String TOPIC_LIKE_DISCUSS_COMMENT = "like_discuss_comment" ;
    /**
     * 消费点赞博文主题
     */
    public static final String TOPIC_LIKE_ARTICLE = "like_article" ;
    /**
     * 消费点赞帖子主题
     */
    public static final String TOPIC_LIKE_DISCUSS = "like_discuss";
    /**
     * 消费发布博文主题
     */
    public static final String TOPIC_PUBLISH_ARTICLE = "publish_article" ;
    /**
     * 消费私信主题
     */
    public static final String TOPIC_LETTER = "letter" ;
    /**
     * 消费关注主题
     */
    public static final String TOPIC_FOLLOW = "follow" ;
    /**
     * 评论类型
     */
    public static final String T_TYPE_COMMENT="comment";
    /**
     * 通知类型
     */
    public static final String T_TYPE_NOTICE="notice";
    /**
     * 点赞类型
     */
    public static final String T_TYPE_LIKE="like";
    /**
     * 私信类型
     */
    public static final String T_TYPE_LETTER="letter";
    /**
     * 关注类型
     */
    public static final String T_TYPE_FOLLOW="follow";
    /**
     * 通知默认未读
     */
    public static final String IS_READ_DEFAULT = "0" ;
    /**
     * 通知已读
     */
    public static final String IS_READ_NORMAL = "1" ;
    /**
     * 点赞类型为文章
     */
    public static final Integer LIKE_TYPE_ARTICLE = 0;
    /**
     * 点赞类型为帖子
     */
    public static final Integer LIKE_TYPE_DISCUSS = 1;
    /**
     * 点赞类型为文章评论
     */
    public static final Integer LIKE_TYPE_COMMENT_ARTICLE = 2;
    /**
     * 点赞类型为帖子评论
     */
    public static final Integer LIKE_TYPE_COMMENT_DISCUSS = 3;

    /**
     * 关注来源是文章详情
     */
    public static final String FOLLOW_ARTICLE_DETAIL = "article_detail";
    /**
     * 关注来源是帖子详情
     */
    public static final String FOLLOW_DISCUSS_DETAIL = "disucss_detail";
    /**
     * 关注来源是个人主页
     */
    public static final String FOLLOW_HOMEPAGE = "homepage";
    /**
     * 关注后的默认回复
     */
    public static final String FOLLOW_LETTER_DEFAULT ="感谢你的关注,欢迎我们一起交流！" ;


}
