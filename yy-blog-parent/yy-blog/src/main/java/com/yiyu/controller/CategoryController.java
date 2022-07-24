package com.yiyu.controller;

import com.yiyu.annotation.SystemLog;
import com.yiyu.dto.front.CategoryDTO;
import com.yiyu.dto.front.UserCategoryDTO;
import com.yiyu.service.CategoryService;
import com.yiyu.utils.ResponseResult;
import com.yiyu.vo.front.HotArtCategoryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分类表(Category)表控制层
 *
 * @author makejava
 * @since 2022-02-14 16:15:10
 */
@Api(tags = "分类模块")
@RestController
@RequestMapping("/category")
public class CategoryController {
    /**
     * 服务对象
     */
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取分类列表")
    @SystemLog(businessName = "获取分类列表")
    @GetMapping("/getCategoryList")
    public ResponseResult<List<CategoryDTO>> getCategoryList() {
        return categoryService.getCategoryList();
    }

    @ApiOperation(value = "获取分类专栏详情")
    @SystemLog(businessName = "获取分类专栏详情")
    @GetMapping("/categoryDetail")
    public ResponseResult<List<UserCategoryDTO>> categoryDetail(HotArtCategoryVo hotArtCategoryVo) {
        return categoryService.getCategoryDetail(hotArtCategoryVo);
    }
    @ApiOperation(value = "获取登录用户分类")
    @SystemLog(businessName = "获取登录用户分类")
    @GetMapping("/categories")
    public ResponseResult<List<UserCategoryDTO>> categories() {
        return categoryService.categories();
    }

}

