package com.yiyu.event;

import com.alibaba.fastjson.JSONObject;
import com.yiyu.constants.NoticeConst;
import com.yiyu.entity.Event;
import com.yiyu.entity.Notice;
import com.yiyu.service.ArticleService;
import com.yiyu.service.NoticeService;
import com.yiyu.service.impl.ElasticSearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;
/**
 * @author zh
 * @ClassName : zh.nb.Event.EventConsumer
 * @Description :
 * Created by user on 2022-05-01 15:07:31
 * Copyright  2020 user. All rights reserved.
 */

@Slf4j
@Component
public class EventConsumer {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private ElasticSearchService elasticSearchService;
    @Autowired
    private ArticleService articleService;

    private static final Logger LOGGER = LoggerFactory.getLogger(EventConsumer.class);

    @KafkaListener(topics = {
            NoticeConst.TOPIC_COMMENT_ARTICLE,
            NoticeConst.TOPIC_COMMENT_DISCUSS,
            NoticeConst.TOPIC_REPLY_DICUSS_COMMENT,
            NoticeConst.TOPIC_REPLY_ARTICLE_COMMENT,
            NoticeConst.TOPIC_LIKE_ARTICLE,
            NoticeConst.TOPIC_LIKE_DISCUSS,
            NoticeConst.TOPIC_LIKE_ARTICLE_COMMENT,
            NoticeConst.TOPIC_LIKE_DISCUSS_COMMENT,
            NoticeConst.TOPIC_LETTER,
            NoticeConst.TOPIC_FOLLOW})
    public void handleMessage(ConsumerRecord<String, String> record) {
        if (record == null || record.value() == null) {
            log.error("消息的内容为空!");
            return;
        }
        Event event = JSONObject.parseObject(record.value(), Event.class);
        if (event == null) {
            log.error("消息格式错误!");
            return;
        }
        Notice notice = new Notice();
        notice.setFromId(event.getSendId()).
                setToId(event.getReceiveId()).
                setType(event.getTopic()).
                setCreateTime(new Date()).
                setTType(event.getEventType()).
                setContent(JSONObject.toJSONString(event.getData()));
        noticeService.addMessage(notice);
    }

    @KafkaListener(topics = {NoticeConst.TOPIC_PUBLISH_ARTICLE})
    public void handleES(ConsumerRecord<String, String> record) {
        if (record == null || record.value() == null) {
            log.error("消息的内容为空!");
            return;
        }

        Event event = JSONObject.parseObject(record.value(), Event.class);
        if (event == null) {
            log.error("消息格式错误!");
            return;
        }
        Long articleId = event.getSendId();
        elasticSearchService.saveArticle(articleService.getById(articleId));
    }
}
