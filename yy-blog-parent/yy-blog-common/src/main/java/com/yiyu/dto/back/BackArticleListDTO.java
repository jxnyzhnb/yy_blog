package com.yiyu.dto.back;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author zh
 * @ClassName : zh.nb.vo.ArticleVo
 * @Description :
 * Created by user on 2022-02-19 15:49:28
 * Copyright  2020 user. All rights reserved.
 */
@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackArticleListDTO {
    /**
     * 主键
     */
    private Long id;
    /**
     * 文章内容
     */
    private String title;
    /**
     * 分类名
     */
    private String categoryName;
    /**
     * 分类id
     */
    private Long categoryId;
    /**
     * 作者
     */
    private String nickName;
    /**
     * 文章摘要
     */
    private String summary;
    /**
     * 缩略图
     */
    private String thumbnail;
    /**
     * 访问量
     */
    private Long viewCount;
    /**
     * 点赞量
     */
    private Integer likeCount;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 标签
     */
    private List<String> tags;

}
