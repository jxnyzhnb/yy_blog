package com.yiyu.dto.back;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author yiyu
 * @version 1.0 2022-06-05 09:19:08
 * @ClassName : com.yiyu.dto.back.BackCategoryDTO
 * @Description :
 */
@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackCategoryDTO {
    /**
     * 分类id
     */
    private Long id;
    /**
     * 分类名
     */
    private String name;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     *创建人
     */
    private String nickName;
    /**
     * 状态
     */
    private Integer status;

}
