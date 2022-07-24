package com.yiyu.controller;

import com.yiyu.annotation.SystemLog;
import com.yiyu.dto.front.CommentListDTO;
import com.yiyu.dto.front.NewCommentDTO;
import com.yiyu.dto.front.PageDTO;
import com.yiyu.service.CommentService;
import com.yiyu.utils.ResponseResult;
import com.yiyu.vo.front.AddCommentVo;
import com.yiyu.vo.front.CommentConditionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评论表(Comment)表控制层
 *
 * @author makejava
 * @since 2022-02-14 16:15:10
 */
@Api(tags = "评论模块")
@RestController
@RequestMapping("/comment")
public class CommentController {
    /**
     * 服务对象
     */
    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "获取评论")
    @SystemLog(businessName = "获取评论")
    @GetMapping("/commentList")
    public ResponseResult<PageDTO<CommentListDTO>> commentList(CommentConditionVo commentConditionVo) {
        return commentService.commentList(commentConditionVo);
    }

    @ApiOperation(value = "增加评论")
    @SystemLog(businessName = "增加评论")
    @PostMapping
    public ResponseResult<Object> addComment(@RequestBody AddCommentVo comment) {
        return commentService.addComment(comment);
    }

    @ApiOperation(value = "获取最新评论")
    @ApiImplicitParam(name = "aid", value = "文章id", required = true, dataType = "Long")
    @SystemLog(businessName = "获取最新评论")
    @GetMapping("/getNewComment")
    public ResponseResult<List<NewCommentDTO>> getNewComment(Long aid) {
        return commentService.getNewComment(aid);
    }
}

