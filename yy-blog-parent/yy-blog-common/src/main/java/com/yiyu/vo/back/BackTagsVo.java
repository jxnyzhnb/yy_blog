package com.yiyu.vo.back;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 * @author yiyu
 * @version 1.0 2022-06-05 19:48:30
 * @ClassName : com.yiyu.vo.back.BackTagsVo
 * @Description :
 */
@ApiModel(description = "后台标签更改")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackTagsVo {
    /**
     * 标签id
     */

    @ApiModelProperty(name = "id", value = "标签id", dataType = "Long")
    private Long id;
    /**
     *标签名称
     */
    @NotNull
    @ApiModelProperty(name = "tagName", value = "标签名称", dataType = "String")
    private String tagName;
}
