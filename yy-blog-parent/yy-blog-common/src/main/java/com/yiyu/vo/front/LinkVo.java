package com.yiyu.vo.front;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yiyu
 * @version 1.0 2022-06-16 19:51:29
 * @ClassName : com.yiyu.vo.front.LinkVo
 * @Description :
 */
@ApiModel(description = "添加友链")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LinkVo {
    /**
     * 友链名称
     */
    @ApiModelProperty(name = "name", value = "友链名称", dataType = "String")
    private String name;
    /**
     * logo
     */
    @ApiModelProperty(name = "logo", value = "logo", dataType = "String")
    private String logo;
    /**
     * 描述
     */
    @ApiModelProperty(name = "description", value = "描述", dataType = "String")
    private String description;
    /**
     * 地址
     */
    @ApiModelProperty(name = "address", value = "地址", dataType = "String")
    private String address;
}
