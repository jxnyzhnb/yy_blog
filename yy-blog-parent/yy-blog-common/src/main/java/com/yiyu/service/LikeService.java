package com.yiyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yiyu.entity.Like;
import com.yiyu.utils.ResponseResult;
import com.yiyu.vo.front.LikeVo;

/**
 * (Like)表服务接口
 *
 * @author makejava
 * @since 2022-05-06 10:54:53
 */
public interface LikeService extends IService<Like> {

    ResponseResult<Object> setLike(LikeVo likeVo) ;

    ResponseResult<Object> unLike( LikeVo likeVo);

    boolean isLike(Long typeId, Integer type);

    int getLikeCount(Long authorId);
}

