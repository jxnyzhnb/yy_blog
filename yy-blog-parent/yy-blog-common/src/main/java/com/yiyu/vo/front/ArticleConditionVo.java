package com.yiyu.vo.front;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 * @author zh
 * @ClassName : com.yiyu.vo.ConditionVo
 * @Description :文章查询列表条件
 * Created by user on 2022-05-16 19:36:03
 * Copyright  2020 user. All rights reserved.
 */
@ApiModel(description = "文章搜索条件")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleConditionVo {
    /**
     * 搜索关键字
     */
    @ApiModelProperty(name = "q", value = "搜索关键字", dataType = "String")
    private String q;
    /**
     * 当前页数
     */
    @NotNull
    @DecimalMin(value = "0",message = "当前页码错误")
    @ApiModelProperty(name = "pageNum", value = "当前页数", dataType = "Integer")
    private Integer pageNum;
    /**
     * 每页数量
     */
    @NotNull
    @ApiModelProperty(name = "pageSize", value = "每页数量", dataType = "Integer")
    private Integer pageSize;
    /**
     * 分类id
     */
    @ApiModelProperty(name = "categoryId", value = "分类id", dataType = "Long")
    private Long categoryId;
    /**
     * 文章列表详情形式(0代表首页形式,1代表我的文章形式,2代表别人的主页形式)
     */
    @ApiModelProperty(name = "type", value = "文章列表详情形式(0代表首页形式,1代表我的文章形式,2代表别人的主页形式)", dataType = "Integer")
    private Integer type;
    /**
     * 用户id,只有在类型2才会传回
     */
    @ApiModelProperty(name = "wid", value = "用户id,只有在类型2才会传回", dataType = "Long")
    private Long wid;
}
