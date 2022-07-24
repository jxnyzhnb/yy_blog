package com.yiyu.vo.front;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yiyu
 * @ClassName : com.yiyu.vo.front.CategoryVo
 * @Description :
 * Created by user on 2022-05-22 12:50:01
 * Copyright  2020 user. All rights reserved.
 */
@ApiModel(description = "分类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVo {
    private Long categoryId;
    private String categoryName;
}
