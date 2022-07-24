package com.yiyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yiyu.entity.Link;
import com.yiyu.utils.ResponseResult;
import com.yiyu.dto.front.LinkDTO;
import com.yiyu.vo.front.LinkVo;

import java.util.List;

/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-02-14 15:58:00
 */
public interface LinkService extends IService<Link> {

    ResponseResult<List<LinkDTO>> getAllLink();

    ResponseResult<Object> addLink(LinkVo linkVo);
}

