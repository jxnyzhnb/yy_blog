package com.yiyu.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zh
 * @ClassName : zh.nb.vo.LinkVo
 * @Description :
 * Created by user on 2022-02-21 10:04:06
 * Copyright  2020 user. All rights reserved.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LinkDTO {
    /**
     * 主键
     */
    private Long id;
    /**
     * 友链名称
     */
    private String name;
    /**
     * logo
     */
    private String logo;
    /**
     * 友链描述
     */
    private String description;
    /**
     * 网站地址
     */
    private String address;

}
