package com.yiyu.vo.front;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yiyu
 * @version 1.0 2022-06-17 14:32:19
 * @ClassName : com.yiyu.vo.front.DiscussConditonVo
 * @Description :
 */
@ApiModel(description = "帖子条件")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscussConditonVo {
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
    /**
     * 帖子类型
     */
    @ApiModelProperty(name = "type", value = "帖子类型", dataType = "Integer")
    private Integer type;
}
