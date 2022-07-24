package com.yiyu.dto.back;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author yiyu
 * @version 1.0 2022-06-02 21:24:23
 * @ClassName : com.yiyu.dto.dmin.ArticleRankDTO
 * @Description :
 */
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticleRankDTO {

    /**
     * 标题
     */
    private String title;
    /**
     * 作者昵称
     */
    private String nickName;

    /**
     * 浏览量
     */
    private Long viewCount;
}
