package com.yiyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yiyu.dto.front.LetterDTO;
import com.yiyu.dto.front.LetterUserDTO;
import com.yiyu.dto.front.NoticeDTO;
import com.yiyu.dto.front.PageDTO;
import com.yiyu.entity.Notice;
import com.yiyu.utils.ResponseResult;
import com.yiyu.vo.front.NoticeConditionVo;

import java.util.List;
import java.util.Map;

/**
 * (Notice)表服务接口
 *
 * @author makejava
 * @since 2022-05-01 16:40:25
 */
public interface NoticeService extends IService<Notice> {

    void addMessage(Notice notice);
    ResponseResult<PageDTO<NoticeDTO>> getNewNotice(NoticeConditionVo conditionVo);

    ResponseResult<Boolean> getIsRead();

    ResponseResult<Map<String, String>> getUnread();

    ResponseResult<Object> setRead(String tType);

    ResponseResult<Object> delete(Long id);

    ResponseResult<List<LetterDTO>> getLetterList(Long toId);

    ResponseResult<List<LetterUserDTO>> getUserList();

    ResponseResult<Object> setLetterRead(Long toId);
}

