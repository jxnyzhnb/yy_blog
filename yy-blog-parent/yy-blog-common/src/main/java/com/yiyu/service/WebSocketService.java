package com.yiyu.service;

import com.yiyu.vo.front.MsgVo;
import com.yiyu.dto.front.LetterDTO;

public interface WebSocketService {
    LetterDTO replyLetter(MsgVo msgVo, Long sid);


}
