package com.yiyu.vo.back;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author yiyu
 * @version 1.0 2022-06-04 22:12:20
 * @ClassName : com.yiyu.vo.back.IdsVo
 * @Description :解决fastjson无法接受集合和数组参数
 */
@ApiModel(description = "id集合对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdsVo {
    @ApiModelProperty(name = "ids", value = "id集合", dataType = "List<Long>")
    /**
     * id集合
     */
    private List<Long>  ids;
}
