package com.yiyu.service;

import org.springframework.web.multipart.MultipartFile;
import com.yiyu.utils.ResponseResult;

/**
 * @author zh
 * @ClassName : zh.nb.service.UploadService
 * @Description :
 * Created by user on 2022-03-02 20:36:21
 * Copyright  2020 user. All rights reserved.
 */
public interface UploadService {
    ResponseResult<Object> uploadImg(MultipartFile img);
}
