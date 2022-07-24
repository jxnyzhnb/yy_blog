package com.yiyu.dto.front;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author zh
 * @ClassName : zh.nb.vo.UserCategoryDTO
 * @Description :
 * Created by user on 2022-02-14 20:28:53
 * Copyright  2020 user. All rights reserved.
 */
@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCategoryDTO {
    /**
     *  分类id
     */
    private Long id;
    /**
     * 分类名
     */
    private String name;
    /**
     * 分类文章数量
     */
    private Integer count;

}
