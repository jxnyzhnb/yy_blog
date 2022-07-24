package com.yiyu.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zh
 * @ClassName : zh.nb.vo.PageVo
 * @Description :
 * Created by user on 2022-02-19 16:20:41
 * Copyright  2020 user. All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO<T> {
    private List<T> rows;
    private Long total;
}
