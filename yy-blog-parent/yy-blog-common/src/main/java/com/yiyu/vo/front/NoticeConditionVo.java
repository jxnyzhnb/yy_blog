package com.yiyu.vo.front;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yiyu
 * @ClassName : com.yiyu.vo.front.NoticeConditionVo
 * @Description :
 * Created by user on 2022-05-16 20:12:25
 * Copyright  2020 user. All rights reserved.
 */
@ApiModel(description = "通知条件")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeConditionVo {

    /**
     * 通知总类型
     */
    @ApiModelProperty(name = "tType", value = "通知总类型", dataType = "String")
    private String tType;
    /**
     * 当前页数
     */
    @ApiModelProperty(name = "pageNum", value = "当前页数", dataType = "Integer")
    private Integer pageNum;
    /**
     * 每页数量
     */
    @ApiModelProperty(name = "pageSize", value = "每页数量", dataType = "Integer")
    private Integer pageSize;
}
