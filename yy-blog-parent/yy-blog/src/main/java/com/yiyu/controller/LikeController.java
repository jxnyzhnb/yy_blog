package com.yiyu.controller;

import com.yiyu.annotation.SystemLog;
import com.yiyu.service.LikeService;
import com.yiyu.utils.ResponseResult;
import com.yiyu.vo.front.LikeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * (Like)表控制层
 *
 * @author makejava
 * @since 2022-05-06 10:54:52
 */
@Api(tags = "点赞模块")
@RestController
@RequestMapping("/like")
public class LikeController {
    /**
     * 服务对象
     */
    @Autowired
    private LikeService likeService;

    @ApiOperation(value = "进行点赞")
    @SystemLog(businessName = "进行点赞")
    @PostMapping("/setLike")
    public ResponseResult<Object> setLike(@RequestBody LikeVo likeVo) {
        return likeService.setLike(likeVo);
    }

    @ApiOperation(value = "取消点赞")
    @SystemLog(businessName = "取消点赞")
    @PostMapping("/unLike")
    public ResponseResult<Object> unLike(@RequestBody LikeVo likeVo) {
        return likeService.unLike(likeVo);
    }

    @ApiOperation(value = "获得用户是否点赞该文章")
    @ApiImplicitParam(name = "id", value = "文章id", required = true, dataType = "Long")
    @SystemLog(businessName = "获得用户是否点赞该文章")
    @GetMapping("/isLike")
    public ResponseResult<Boolean> getIsLike(LikeVo likeVo) {
        return ResponseResult.okResult(likeService.isLike(likeVo.getTypeId(),likeVo.getType()));
    }
}


