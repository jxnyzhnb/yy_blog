package com.yiyu.constants;

/**
 * @author yiyu
 * @version 1.0 2022-06-02 15:28:36
 * @ClassName : com.yiyu.constants.AdminConst
 * @Description :
 */
public class AdminConst {
    /**
     * 一级菜单根id
     */
    public static final Long MENU_ROOT_ID_DEFAULT = -1L;
    /**
     * 前端根菜单的组件名
     */
    public static final String MENU_ROOT_COMPONMENT = "Layout";
    /**
     * redis独立访客前缀
     */
    public static final String UNIQUE_VIEW_COUNT = "blog:unique_view_count";
    /**
     * ip集合前缀
     */
    public static final String IP_SET = "blog:ip_set";
    /**
     * 间隔天数为0
     */
    public static final int UV_INTERVAL_DAYS_ZERO = 0;
    /**
     * 查询UV时的默认间隔天数
     */
    public static final int UV_INTERVAL_DAYS_DEFAULT = 7;
    /**
     * 后台显示热门文章排行数量
     */
    public static final long ADMIN_ARTICLE_RANK_COUNT = 5L;
}
