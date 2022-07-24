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
 * @ClassName : com.yiyu.vo.back.BackConditionVo
 * @Description :
 * @version 1.0 2022-06-05 09:43:08
 */
@ApiModel(description = "后台列表查询")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackConditionVo {
    /**
     * 当前页数
     */
    @NotNull
    @DecimalMin(value = "0",message = "当前页码错误")
    @ApiModelProperty(name = "pageNum", value = "当前页数", dataType = "Integer")
    private Integer pageNum;
    /**
     * 每页数量
     */
    @NotNull
    @ApiModelProperty(name = "pageSize", value = "每页数量", dataType = "Integer")
    private Integer pageSize;
    /**
     * 关键字
     */
    @ApiModelProperty(name = "q", value = "关键字", dataType = "String")
    private String q;

}
