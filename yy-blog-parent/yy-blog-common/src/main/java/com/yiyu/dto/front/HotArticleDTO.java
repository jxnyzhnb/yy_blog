package com.yiyu.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zh
 * @ClassName : zh.nb.vo.HotArtCategoryVo
 * @Description :
 * Created by user on 2022-02-14 20:28:53
 * Copyright  2020 user. All rights reserved.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotArticleDTO {
    private Long id;
    //标题
    private String title;
    //访问量
    private Long viewCount;
    //缩略图
    private String thumbnail;

}
