package com.yiyu.controller;

import com.yiyu.dto.back.BlogBackInfoDTO;
import com.yiyu.service.SiteInfoService;
import com.yiyu.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yiyu
 * @version 1.0 2022-06-02 21:29:34
 * @ClassName : com.yiyu.controller.SiteInfoController
 * @Description :网站基本信息
 */
@RequestMapping("/siteInfo")
@RestController
public class SiteInfoController {
    @Autowired
    private SiteInfoService siteInfoService;
    @GetMapping
    public ResponseResult<BlogBackInfoDTO> siteInfo(){
        return siteInfoService.getBlogBackInfo();
    }

}
