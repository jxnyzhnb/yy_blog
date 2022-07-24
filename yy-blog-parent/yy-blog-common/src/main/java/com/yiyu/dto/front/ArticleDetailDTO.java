package com.yiyu.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author zh
 * @ClassName : zh.nb.vo.ArticleDetailVo
 * @Description :
 * Created by user on 2022-02-21 08:52:03
 * Copyright  2020 user. All rights reserved.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailDTO {
    /**
     * 主键
     */
    private Long id;
    /**
     * 文章内容
     */
    private String title;
    /**
     * 文章标签
     */
    private List<String> tags;
    /**
     * 分类id
     */
    private Long categoryId;
    /**
     * 分类名
     */
    private String categoryName;
    /**
     * 作者
     */
    private String nickName;
    /**
     * 文章摘要
     */
    private String summary;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 缩略图
     */
    private String thumbnail;
    /**
     * 访问量
     */
    private Long viewCount;
    /**
     * 点赞数
     */
    private Integer likeCount;
    /**
     * 创建时间
     */
    private Date createTime;

}
