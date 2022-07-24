package com.yiyu.controller;

import com.yiyu.annotation.SystemLog;
import com.yiyu.dto.back.BackCategoryDTO;
import com.yiyu.dto.front.PageDTO;
import com.yiyu.service.CategoryService;
import com.yiyu.utils.ResponseResult;
import com.yiyu.vo.back.BackConditionVo;
import com.yiyu.vo.back.IdsVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yiyu
 * @version 1.0 2022-06-05 09:16:48
 * @ClassName : com.yiyu.controller.CategoryController
 * @Description :
 */
@RequestMapping("/admin/categories")
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseResult<PageDTO<BackCategoryDTO>> categories(BackConditionVo backCategoryVo) {
        return categoryService.categoryList(backCategoryVo);
    }

    @PutMapping("/status/{id}")
    public ResponseResult<Object> updateStatus(@PathVariable("id") Long id, String status) {
        return categoryService.updateStatus(id, status);
    }

    @ApiOperation(value = "后台删除分类")
    @SystemLog(businessName = "后台删除分类")
    @PutMapping
    public ResponseResult<Object> articles(@RequestBody IdsVo idsVo) {
        return categoryService.deleteCategory(idsVo);
    }
}
