package com.yiyu.controller;

import com.yiyu.annotation.SystemLog;
import com.yiyu.service.TagsService;
import com.yiyu.utils.ResponseResult;
import com.yiyu.dto.front.TagsDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zh
 * @ClassName : zh.nb.controller.TagsController
 * @Description :
 * Created by user on 2022-05-04 12:14:32
 * Copyright  2020 user. All rights reserved.
 */
@Api(tags = "标签模块")
@RestController
@RequestMapping("/tags")
public class TagsController {
    @Autowired
    private TagsService tagsService;

    @ApiOperation(value = "获取所有文章标签")
    @SystemLog(businessName = "获取所有文章标签")
    @GetMapping("/getTagsList")
    public ResponseResult<List<TagsDTO>> getTagsList() {
        return tagsService.getTagsList();
    }
}
