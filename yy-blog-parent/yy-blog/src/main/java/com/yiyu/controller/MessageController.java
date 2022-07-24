package com.yiyu.controller;

import com.yiyu.annotation.SystemLog;
import com.yiyu.dto.front.MessageDTO;
import com.yiyu.service.MessageService;
import com.yiyu.utils.ResponseResult;
import com.yiyu.vo.front.MessageVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * (Message)表控制层
 *
 * @author makejava
 * @since 2022-06-03 10:15:02
 */
@RestController
@RequestMapping("/messages")
public class MessageController {
    /**
     * 服务对象
     */

    @Autowired
    private MessageService messageService;

    @ApiOperation(value = "添加留言")
    @SystemLog(businessName = "添加留言")
    @PostMapping
    public ResponseResult<Object> message(@Valid @RequestBody MessageVo messageVo) {
        return messageService.saveMessage(messageVo);
    }
    @ApiOperation(value = "获得留言列表")
    @SystemLog(businessName = "获得留言列表")
    @GetMapping
    public ResponseResult<List<MessageDTO>> message() {
        return messageService.getMessage();
    }

}

