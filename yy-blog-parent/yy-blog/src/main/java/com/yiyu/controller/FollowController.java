package com.yiyu.controller;
import com.yiyu.annotation.SystemLog;
import com.yiyu.service.FollowService;
import com.yiyu.utils.ResponseResult;
import com.yiyu.vo.front.FollowVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * (Follow)表控制层
 *
 * @author makejava
 * @since 2022-05-17 21:03:14
 */
@RestController
@RequestMapping("/follow")
public class FollowController{
    /**
     * 服务对象
     */
    @Autowired
    private FollowService followService;

    @ApiOperation(value ="关注作者" )
    @ApiImplicitParam(name = "toUserId", value = "被关注用户id", required = true, dataType = "Long")
    @SystemLog(businessName = "关注作者")
    @PostMapping
    public ResponseResult<Object> follow(@RequestBody FollowVo followVo){
        return followService.follow(followVo);
    }
    @ApiOperation(value ="取消关注作者" )
    @ApiImplicitParam(name = "toUserId", value = "被关注用户id", required = true, dataType = "Long")
    @SystemLog(businessName = "查询文章是否被用户关注")
    @PostMapping("/unFollow")
    public ResponseResult<Object> unFollow(@RequestBody FollowVo followVo){
        return followService.unFollow(followVo);
    }

}

