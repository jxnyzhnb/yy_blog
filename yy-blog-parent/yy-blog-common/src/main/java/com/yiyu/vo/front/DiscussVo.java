package com.yiyu.vo.front;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yiyu
 * @version 1.0 2022-06-18 15:30:50
 * @ClassName : com.yiyu.vo.front.DiscussVo
 * @Description :
 */
@ApiModel(description = "添加帖子")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscussVo {
    /**
     * 帖子标题
     */
    @ApiModelProperty(name = "title", value = "帖子标题", dataType = "String")
    private String title;
    /**
     * 帖子内容
     */
    @ApiModelProperty(name = "content", value = "帖子内容", dataType = "String")
    private String content;
}
