package com.yiyu.controller;

import com.yiyu.annotation.SystemLog;
import com.yiyu.dto.back.BackTagsDTO;
import com.yiyu.dto.front.PageDTO;
import com.yiyu.service.TagsService;
import com.yiyu.utils.ResponseResult;
import com.yiyu.vo.back.BackConditionVo;
import com.yiyu.vo.back.BackTagsVo;
import com.yiyu.vo.back.IdsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author yiyu
 * @version 1.0 2022-06-05 19:08:35
 * @ClassName : com.yiyu.controller.TagsController
 * @Description :
 */
@RequestMapping("/admin")
@RestController
public class TagsController {
    @Autowired
    private TagsService tagsService;
    @SystemLog(businessName = "获取后台标签")
    @GetMapping("/tags")
    public ResponseResult<PageDTO<BackTagsDTO>> tags(@Valid BackConditionVo conditionVo){
        return tagsService.tagsList(conditionVo);
    }
    @SystemLog(businessName = "修改或添加后台标签")
    @PostMapping("/tags")
    public ResponseResult<Object> updateTags(@Valid @RequestBody BackTagsVo backTagsVo){
        return tagsService.updateOrAddTags(backTagsVo);
    }
    @SystemLog(businessName = "删除后台标签")
    @DeleteMapping("/tags")
    public ResponseResult<Object> deleteTags(@RequestBody IdsVo idsVo){
        return tagsService.deleteTags(idsVo);
    }
}
