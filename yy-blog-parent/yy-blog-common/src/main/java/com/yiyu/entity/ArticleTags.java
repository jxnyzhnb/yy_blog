package com.yiyu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (ArticleTags)表实体类
 *
 * @author makejava
 * @since 2022-05-04 16:20:31
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticleTags {
    /**
     * 文章id
     */

    private Long articleId;
    /**
     * 标签id
     */

    private Long tagsId;
}

