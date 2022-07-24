package com.yiyu.service.impl;

import com.yiyu.constants.NoticeConst;
import com.yiyu.entity.Event;
import com.yiyu.entity.User;
import com.yiyu.service.NoticeService;
import com.yiyu.service.UserService;
import com.yiyu.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yiyu.vo.front.MsgVo;
import com.yiyu.event.EventProducer;
import com.yiyu.dto.front.LetterDTO;

import java.util.HashMap;

/**
 * @author zh
 * @ClassName : zh.nb.service.impl.WebSocketServiceImpl
 * @Description :
 * Created by user on 2022-05-13 09:00:00
 * Copyright  2020 user. All rights reserved.
 */
@Service
public class WebSocketServiceImpl implements WebSocketService {
    @Autowired
    private UserService userService;
    @Autowired
    private EventProducer producer;
    @Autowired
    private NoticeService noticeService;
    @Override
    public LetterDTO replyLetter(MsgVo msgVo, Long sid) {
        Event event = new Event();
        HashMap<String, Object> content = new HashMap<>();
        content.put("msg", msgVo.getContent());
        event.setTopic(NoticeConst.TOPIC_LETTER)
                .setEventType(NoticeConst.T_TYPE_LETTER)
                .setSendId(sid)
                .setReceiveId(msgVo.getToId())
                .setData(content);
        producer.fireEvent(event);
        User user = userService.getById(sid);
        LetterDTO letters = new LetterDTO();
        letters.setAvatar(user.getAvatar())
                .setContent(msgVo.getContent())
                .setCreateTime(msgVo.getCreateTime())
                .setSelf(true)
                .setFromId(sid);
        return letters;
    }

}
