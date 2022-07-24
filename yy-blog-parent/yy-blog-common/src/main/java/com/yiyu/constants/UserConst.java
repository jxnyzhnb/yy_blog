package com.yiyu.constants;

/**
 * @author yiyu
 * @ClassName : com.yiyu.constants.LoginConst
 * @Description :
 * Created by user on 2022-05-18 16:45:09
 * Copyright  2020 user. All rights reserved.
 */
public class UserConst {
    /**
     * 用户名和密码错误
     */
    public static final String USERNAME_PASSWORD_FAIL = "用户名或密码错误";
    /**
     * 前台用户登录存入redis中数据的前缀
     */
    public static final String BLOG_LOGIN_USER_REDIS_PRE = "blogLogin:";
    /**
     * 后台用户登录存入redis中数据的前缀
     */
    public static final String ADMIN_LOGIN_USER_REDIS_PRE = "adminLogin:";
    /**
     * 用户通过博客详情获取个人博客信息的来源
     */
    public static final String SOURCE_ARTICLE_DETAIL = "article_detail";
    /**
     * 用户通过个人主页获取个人博客信息的来源
     */
    public static final String SOURCE_HOME_PAGE = "homepage";
    /**
     * 用户通过帖子获取个人博客信息的来源
     */
    public static final String SOURCE_DISCUSS_DETAIL = "discuss_detail";
    /**
     * 用户默认类型--普通用户
     */
    public static final Integer USER_TYPE_DEFAULT = 1;
    /**
     * 管理员类型
     */
    public static final Integer USER_TYPE_ADMIN = 0;
    /**
     * 用户状态为正常
     */
    public static final Integer USER_STATUS_NORMAL = 0;
    /**
     * 用户状态为禁用
     */
    public static final Integer USER_STATUS_ERROR = 1;
}
