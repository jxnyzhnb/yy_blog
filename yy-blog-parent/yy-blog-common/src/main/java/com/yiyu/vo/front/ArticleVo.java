package com.yiyu.vo.front;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author zh
 * @ClassName : zh.nb.dto.ArticleDTO
 * @Description :
 * Created by user on 2022-04-26 21:23:56
 * Copyright  2020 user. All rights reserved.
 */
@ApiModel(description = "添加文章")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVo {
    /**
     * 文章标题
     */
    @NotNull
    @Length(min=3, max=30,message = "文章标题长度不合法")
    @ApiModelProperty(name = "title", value = "文章标题", dataType = "String")
    private String title;
    /**
     * 文章内容
     */
    @NotNull
    @ApiModelProperty(name = "content", value = "文章内容", dataType = "String")
    private String content;
    /**
     * 文章分类id
     */
    @ApiModelProperty(name = "categoryId", value = "文章分类id", dataType = "Long")
    private Long categoryId;
    /**
     * 文章分类
     */
    @NotNull
    @ApiModelProperty(name = "categoryName", value = "文章分类", dataType = "String")
    private String categoryName;
    /**
     * 文章缩略图
     */
    @NotNull
    @ApiModelProperty(name = "thumbnail", value = "文章缩略图", dataType = "String")
    private String thumbnail;
    /**
     * 文章是否置顶
     */
    @NotNull
    @ApiModelProperty(name = "isTop", value = "文章是否置顶", dataType = "String")
    private Integer isTop;
    /**
     * 文章摘要
     */
    @NotNull
    @Length(min=3,message = "文章摘要太短")
    @ApiModelProperty(name = "summary", value = "文章摘要", dataType = "String")
    private String summary;
    /**
     * 文章标签
     */
    @NotNull
    @ApiModelProperty(name = "tagsIds", value = "文章标签", dataType = "Long[]")
    private Long[] tagsIds;

}
