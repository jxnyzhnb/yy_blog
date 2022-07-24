package com.yiyu.event;

import com.alibaba.fastjson.JSONObject;
import com.yiyu.entity.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author zh
 * @ClassName : zh.nb.Event.EventProducer
 * @Description :
 * Created by user on 2022-05-01 15:07:14
 * Copyright  2020 user. All rights reserved.
 */
@Component
public class EventProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(EventProducer.class);
    // 处理事件
    public void fireEvent(Event event) {
        // 将事件发布到指定的主题
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
    }

}
