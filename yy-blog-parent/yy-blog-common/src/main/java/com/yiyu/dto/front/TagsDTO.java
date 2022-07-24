package com.yiyu.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author zh
 * @ClassName : zh.nb.vo.TagsVo
 * @Description :
 * Created by user on 2022-05-04 12:17:49
 * Copyright  2020 user. All rights reserved.
 */
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TagsDTO {
    private Long id;
    private String tagName;
}
