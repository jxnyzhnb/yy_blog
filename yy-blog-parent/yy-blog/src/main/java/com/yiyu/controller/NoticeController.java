package com.yiyu.controller;

import com.yiyu.annotation.SystemLog;
import com.yiyu.dto.front.LetterDTO;
import com.yiyu.dto.front.LetterUserDTO;
import com.yiyu.dto.front.NoticeDTO;
import com.yiyu.dto.front.PageDTO;
import com.yiyu.service.NoticeService;
import com.yiyu.utils.ResponseResult;
import com.yiyu.vo.front.NoticeConditionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author zh
 * @ClassName : zh.nb.controller.NoticeController
 * @Description :
 * Created by user on 2022-05-01 10:28:09
 * Copyright  2020 user. All rights reserved.
 */
@Api(tags = "通知模块")
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @ApiOperation(value = "获取是否有未读消息")
    @SystemLog(businessName = "获取是否有未读消息")
    @GetMapping("/getIsRead")
    public ResponseResult<Boolean> getIsRead() {
        return noticeService.getIsRead();
    }

    @ApiOperation(value = "获取指定类型通知")
    @SystemLog(businessName = "获取指定类型通知")
    @GetMapping("/getNewNotice")
    public ResponseResult<PageDTO<NoticeDTO>> getNewNotice(NoticeConditionVo conditionVo) {
        return noticeService.getNewNotice(conditionVo);
    }

    @ApiOperation(value = "获取各种未读通知数量")
    @SystemLog(businessName = "获取各种未读通知数量")
    @GetMapping("/getUnread")
    public ResponseResult<Map<String, String>> getUnread() {
        return noticeService.getUnread();
    }

    @ApiOperation(value = "更改指定类型通知为已读")
    @ApiImplicitParam(name = "tType", value = "通知类型", required = true, dataType = "String")
    @SystemLog(businessName = "更改指定类型通知为已读")
    @PutMapping("/setRead/{tType}")
    public ResponseResult<Object> setRead(@PathVariable("tType") String tType) {
        return noticeService.setRead(tType);
    }

    @ApiOperation(value = "删除通知")
    @ApiImplicitParam(name = "id", value = "通知id", required = true, dataType = "Long")
    @SystemLog(businessName = "删除通知")
    @DeleteMapping("/delete/{id}")
    public ResponseResult<Object> delete(@PathVariable("id") Long id) {
        return noticeService.delete(id);
    }

    @ApiOperation(value = "获取与用户的私信消息列表")
    @ApiImplicitParam(name = "toId", value = "用户私信的另一个用户的id", required = true, dataType = "Long")
    @SystemLog(businessName = "获取与用户的私信消息列表")
    @GetMapping("/getLetterList")
    public ResponseResult<List<LetterDTO>> getLetterList(Long toId) {
        return noticeService.getLetterList(toId);
    }

    @ApiOperation(value = "获取私信用户列表")
    @SystemLog(businessName = "获取私信用户列表")
    @GetMapping("/getUserList")
    public ResponseResult<List<LetterUserDTO>> getUserList() {
        return noticeService.getUserList();
    }

    @ApiOperation(value = "更改私信消息为已读")
    @ApiImplicitParam(name = "toId", value = "用户私信的另一个用户的id", required = true, dataType = "Long")
    @SystemLog(businessName = "更改私信消息为已读")
    @PutMapping("/setLetterRead/{toId}")
    public ResponseResult<Object> setLetterRead(@PathVariable("toId") Long toId) {
        return noticeService.setLetterRead(toId);
    }
}
