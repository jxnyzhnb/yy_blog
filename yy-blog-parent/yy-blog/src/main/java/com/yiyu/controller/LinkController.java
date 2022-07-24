package com.yiyu.controller;

import com.yiyu.annotation.SystemLog;
import com.yiyu.service.LinkService;
import com.yiyu.utils.ResponseResult;
import com.yiyu.dto.front.LinkDTO;
import com.yiyu.vo.front.LinkVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 友链(Link)表控制层
 *
 * @author makejava
 * @since 2022-02-14 16:15:10
 */
@Api(tags = "友链模块")
@RestController
@RequestMapping("/link")
public class LinkController {
    /**
     * 服务对象
     */
    @Autowired
    private LinkService linkService;

    @ApiOperation(value = "获取友链列表")
    @SystemLog(businessName = "获取友链列表")
    @GetMapping
    public ResponseResult<List<LinkDTO>> getAllLink() {
        return linkService.getAllLink();
    }
    @PostMapping
    public ResponseResult<Object> addLink(@RequestBody LinkVo linkVo ) {
        return linkService.addLink(linkVo);
    }
}

