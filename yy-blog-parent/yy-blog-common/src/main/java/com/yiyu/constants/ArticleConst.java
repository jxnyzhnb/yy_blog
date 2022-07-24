package com.yiyu.constants;

/**
 * @author yiyu
 * @ClassName : com.yiyu.constants.ArticleConst
 * @Description :
 * Created by user on 2022-05-18 16:42:31
 * Copyright  2020 user. All rights reserved.
 */
public class ArticleConst {
    /**
     *  文章是发布的
     */
    public static final int ARTICLE_STATUS_NORMAL=0;
    /**
     * 文章是草稿,未发布
     */
    public static final int ARTICLE_STATUS_DEFAULT=1;
    /**
     * 热门文章的显示数量
     */
    public static final long ARTICLE_HOT_COUNT = 10;
    /**
     * 热门文章的显示当前页
     */
    public static final long ARTICLE_HOT_CURRENT_PAGE = 1;
    /**
     * 分类为正常
     */
    public static final Integer CATEGORY_STATUS_NORMAL = 0;
    /**
     * 分类为禁用
     */
    public static final Integer CATEGORY_STATUS_DEFAULT = 1;
    /**
     * tag的根标签默认值
     */
    public static final long TAGS_ROOT_DEFAULT = -1L;
    /**
     * 标签类型为根标签
     */
    public static final String TAGS_TYPE_ROOT = "根标签";
    /**
     * 标签类型为子标签
     */
    public static final String TAGS_TYPE_CHILD = "子标签";
    /**
     * 根标签为无
     */
    public static final String TAGS_ROOT_NULL="无";
    /**
     * 关注时id不存在的默认值
     */
    public static final long FOLLOW_ID_NULL = -1L;
    /**
     * 关注类型为文章
     */
    public static final String FOLLOW_TYPE_ARTICLE = "article";
    /**
     * 关注类型为帖子
     */
    public static final String FOLLOW_TYPE_DISCUSS = "discuss";
    /**
     * 关注类型为其他
     */
    public static final String FOLLOW_TYPE_OTHER = "other";
    /**
     * 文章列表查询方式为默认,即只有分页条件
     */
    public static final Integer ARTICLE_QUERY_TYPE_DEFAULT =0 ;
    /**
     * 文章列表查询方式为查自己的文章列表
     */
    public static final Integer ARTICLE_QUERY_TYPE_SELF =1 ;
    /**
     * 文章列表查询方式为查询别人的文章列表
     */
    public static final Integer ARTICLE_QUERY_TYPE_OTHER =2 ;
    /**
     * 热门文章列表查询方式为默认,即没有条件
     */
    public static final Integer HOT_ARTICLE_TYPE_DEFAULT =0 ;
    /**
     * 文章列表查询方式为查自己的文章列表
     */
    public static final Integer HOT_ARTICLE_TYPE_SELF =1 ;
    /**
     * 文章列表查询方式为查询别人的文章列表
     */
    public static final Integer HOT_ARTICLE_TYPE_OTHER =2 ;
    /**
     * 文章列表查询方式为查询文章详情
     */
    public static final Integer HOT_ARTICLE_TYPE_DETAIL =3;

}
