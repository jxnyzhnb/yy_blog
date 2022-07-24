package com.yiyu.vo.front;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zh
 * @ClassName : zh.nb.dto.MsgDTO
 * @Description :
 * Created by user on 2022-05-12 22:34:02
 * Copyright  2020 user. All rights reserved.
 */
@ApiModel(description = "消息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsgVo {
    /**
     * 接收消息的用户id
     */
    @ApiModelProperty(name = "toId", value = "接收消息的用户id", dataType = "Long")
    private Long toId;
    /**
     * 消息类型
     */
    @ApiModelProperty(name = "type", value = "消息类型", dataType = "String")
    private String type;
    /**
     * 消息内容
     */
    @ApiModelProperty(name = "content", value = "消息内容", dataType = "String")
    private String content;
    /**
     * 创建时间
     */
    @ApiModelProperty(name = "createTime", value = "创建时间", dataType = "Date")
    private Date createTime;
}
