package com.yiyu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author zh
 * @ClassName : zh.nb.entity.Event
 * @Description :
 * Created by user on 2022-05-01 15:07:54
 * Copyright  2020 user. All rights reserved.
 */
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Event {
    /**
     * 事件的主题
     **/
    private String topic;
    /**
     * 事件的发出者
     **/
    private  Long sendId;
    /**
     * 事件的接受者
     **/
    private  Long receiveId;
    /**
     * 事件的类型
     **/
    private  String eventType;
    /**
     * 额外信息(比如博文id_评论id)
     **/
    private Map<String,Object> data;

}
