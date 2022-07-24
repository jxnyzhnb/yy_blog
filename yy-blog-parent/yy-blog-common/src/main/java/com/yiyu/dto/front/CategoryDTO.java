package com.yiyu.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zh
 * @ClassName : zh.nb.vo.CategoryListVo
 * @Description :
 * Created by user on 2022-02-14 21:28:51
 * Copyright  2020 user. All rights reserved.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    /**
     * 主键
     */
    private Long id;
    /**
     * 分类名称
     */
    private String name;
}
