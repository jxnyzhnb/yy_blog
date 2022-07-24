package com.yiyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yiyu.dto.back.BackTagsDTO;
import com.yiyu.dto.front.PageDTO;
import com.yiyu.entity.Tags;
import com.yiyu.utils.ResponseResult;
import com.yiyu.dto.front.TagsDTO;
import com.yiyu.vo.back.BackConditionVo;
import com.yiyu.vo.back.BackTagsVo;
import com.yiyu.vo.back.IdsVo;

import java.util.List;

/**
 * (Tags)表服务接口
 *
 * @author makejava
 * @since 2022-05-03 20:13:53
 */
public interface TagsService extends IService<Tags> {

    ResponseResult<List<TagsDTO>> getTagsList();
    ResponseResult<PageDTO<BackTagsDTO>> tagsList(BackConditionVo backConditionVo);

    ResponseResult<Object> updateOrAddTags(BackTagsVo backTagsVo);

    ResponseResult<Object> deleteTags(IdsVo idsVo);
}

