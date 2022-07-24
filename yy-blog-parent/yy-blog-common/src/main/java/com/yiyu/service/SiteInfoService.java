package com.yiyu.service;

import com.yiyu.dto.back.BlogBackInfoDTO;
import com.yiyu.utils.ResponseResult;

public interface SiteInfoService {
    ResponseResult<BlogBackInfoDTO> getBlogBackInfo();
}
