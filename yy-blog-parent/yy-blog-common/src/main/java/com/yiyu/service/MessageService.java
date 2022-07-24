package com.yiyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yiyu.dto.front.MessageDTO;
import com.yiyu.entity.Message;
import com.yiyu.utils.ResponseResult;
import com.yiyu.vo.front.MessageVo;

import java.util.List;

/**
 * (Message)表服务接口
 *
 * @author makejava
 * @since 2022-06-03 10:15:03
 */
public interface MessageService extends IService<Message> {

    ResponseResult<Object> saveMessage(MessageVo messageVo);

    ResponseResult<List<MessageDTO>> getMessage();
}

