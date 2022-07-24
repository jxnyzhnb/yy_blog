package com.yiyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yiyu.dto.front.DiscussDetailDTO;
import com.yiyu.dto.front.DiscussListDTO;
import com.yiyu.dto.front.PageDTO;
import com.yiyu.entity.Discuss;
import com.yiyu.utils.ResponseResult;
import com.yiyu.vo.front.DiscussConditonVo;
import com.yiyu.vo.front.DiscussVo;

/**
 * (Discuss)表服务接口
 *
 * @author makejava
 * @since 2022-06-17 14:09:11
 */
public interface DiscussService extends IService<Discuss> {

    ResponseResult<PageDTO<DiscussListDTO>> discussList(DiscussConditonVo discussConditonVo);

    ResponseResult<Object> addDiscuss(DiscussVo discussVo);

    ResponseResult<DiscussDetailDTO> getDiscussDetail(Long did);

    ResponseResult<Object> incrViewCount(Long discussId);

    void viewCount(Long discussId, Integer viewCount);
}

