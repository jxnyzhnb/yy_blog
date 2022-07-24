package com.yiyu.controller;

import com.yiyu.annotation.SystemLog;
import com.yiyu.dto.front.DiscussDetailDTO;
import com.yiyu.dto.front.DiscussListDTO;
import com.yiyu.dto.front.PageDTO;
import com.yiyu.service.DiscussService;
import com.yiyu.utils.ResponseResult;
import com.yiyu.vo.front.DiscussConditonVo;
import com.yiyu.vo.front.DiscussVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author yiyu
 * @version 1.0 2022-06-17 13:59:58
 * @ClassName : com.yiyu.controller.DiscussController
 * @Description :
 */
@RequestMapping("/discuss")
@RestController
public class DiscussController {
    @Autowired
    private DiscussService discussService;
    @GetMapping
    public ResponseResult<PageDTO<DiscussListDTO>> discussList(DiscussConditonVo discussConditonVo){
        return discussService.discussList(discussConditonVo);
    }
    @PostMapping
    public ResponseResult<Object> addDiscuss(@RequestBody DiscussVo discussVo){
        return discussService.addDiscuss(discussVo);
    }
    @GetMapping("/{did}")
    public ResponseResult<DiscussDetailDTO> discussDetail(@PathVariable("did") Long  did){
        return discussService.getDiscussDetail(did);
    }
    @ApiOperation(value = "更新帖子浏览量")
    @ApiImplicitParam(name = "discussId", value = "帖子id", required = true, dataType = "Long")
    @SystemLog(businessName = "更新帖子浏览量")
    @PutMapping("/view/{discussId}")
    public ResponseResult<Object> updateViewCount(@NotNull @PathVariable("discussId") Long discussId) {
        return discussService.incrViewCount(discussId);
    }

}
