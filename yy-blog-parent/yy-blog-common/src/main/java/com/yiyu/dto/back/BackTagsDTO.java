package com.yiyu.dto.back;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author yiyu
 * @version 1.0 2022-06-05 19:10:43
 * @ClassName : com.yiyu.dto.back.BackTagsDTO
 * @Description :
 */
@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackTagsDTO {
    /**
     * 标签id
     */
    private Long id;
    /**
     * 标签名
     */
    private String tagName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String nickName;

}
