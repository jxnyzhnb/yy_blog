package com.yiyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yiyu.dto.front.NewCommentDTO;
import com.yiyu.entity.Comment;
import com.yiyu.vo.front.AddCommentVo;
import com.yiyu.utils.ResponseResult;
import com.yiyu.vo.front.CommentConditionVo;
import com.yiyu.dto.front.CommentListDTO;
import com.yiyu.dto.front.PageDTO;

import java.util.List;

/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2022-02-14 15:58:00
 */
public interface CommentService extends IService<Comment> {

    ResponseResult<PageDTO<CommentListDTO>> commentList(CommentConditionVo commentConditionVo);

    ResponseResult<Object> addComment(AddCommentVo comment) ;
    int getCommentCount(Long userId);
    ResponseResult<List<NewCommentDTO>> getNewComment(Long aid);
}

