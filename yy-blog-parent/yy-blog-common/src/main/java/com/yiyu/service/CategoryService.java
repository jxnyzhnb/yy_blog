package com.yiyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yiyu.dto.back.BackCategoryDTO;
import com.yiyu.dto.front.CategoryDTO;
import com.yiyu.dto.front.PageDTO;
import com.yiyu.dto.front.UserCategoryDTO;
import com.yiyu.entity.Category;
import com.yiyu.utils.ResponseResult;
import com.yiyu.vo.back.BackConditionVo;
import com.yiyu.vo.back.IdsVo;
import com.yiyu.vo.front.HotArtCategoryVo;

import java.util.List;

/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-02-14 15:58:00
 */
public interface CategoryService extends IService<Category> {

    ResponseResult<List<CategoryDTO>> getCategoryList();

    ResponseResult<List<UserCategoryDTO>> getCategoryDetail(HotArtCategoryVo hotArtCategoryVo);

    ResponseResult<List<UserCategoryDTO>> categories();

    List<UserCategoryDTO> getCategoryByName();

    ResponseResult<PageDTO<BackCategoryDTO>> categoryList(BackConditionVo backCategoryVo);

    ResponseResult<Object> updateStatus(Long id, String status);

    ResponseResult<Object> deleteCategory(IdsVo idsVo);
}

