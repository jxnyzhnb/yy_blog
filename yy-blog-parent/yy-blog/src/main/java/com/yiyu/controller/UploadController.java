package com.yiyu.controller;

import com.yiyu.annotation.SystemLog;
import com.yiyu.service.UploadService;
import com.yiyu.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zh
 * @ClassName : zh.nb.controller.UploadController
 * @Description :
 * Created by user on 2022-03-02 20:34:04
 * Copyright  2020 user. All rights reserved.
 */
@Api(tags = "上传模块")
@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @ApiOperation(value = "上传图片")
    @ApiImplicitParam(name = "img", value = "上传文件", required = true, dataType = "MultipartFile")
//    @SystemLog(businessName = "上传图片")
    @PostMapping("/upload")
    public ResponseResult<Object> uploadImg(MultipartFile img) {
        return uploadService.uploadImg(img);
    }
}
