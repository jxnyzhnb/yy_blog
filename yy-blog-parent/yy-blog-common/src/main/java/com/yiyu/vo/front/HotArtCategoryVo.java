package com.yiyu.vo.front;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yiyu
 * @ClassName : com.yiyu.vo.front.HotArtCategoryVo
 * @Description :
 * Created by user on 2022-05-21 16:08:17
 * Copyright  2020 user. All rights reserved.
 */
@ApiModel(description = "关注")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotArtCategoryVo {
    /**
     * 用户id
     */
    @ApiModelProperty(name = "wid", value = "用户id", dataType = "Long")
    private Long wid;
    /**
     * 页面类型(3为ArticleDetail页面,1为MyArticle页面,2为HomePage,0为Home主页)
     */
    @ApiModelProperty(name = "type", value = "页面类型", dataType = "Integer")
    private Integer type;
    /**
     * 文章id
     */
    @ApiModelProperty(name = "aid", value = "文章id", dataType = "Long")
    private Long aid;
}
