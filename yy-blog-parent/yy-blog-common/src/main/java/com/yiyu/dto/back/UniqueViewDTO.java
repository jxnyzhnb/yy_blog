package com.yiyu.dto.back;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author yiyu
 * @version 1.0 2022-06-02 21:24:06
 * @ClassName : com.yiyu.dto.back.UniqueViewDTO
 * @Description :
 */
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UniqueViewDTO {
    /**
     * 日期
     */
    private String day;

    /**
     * 访问量
     */
    private Integer viewsCount;
}
