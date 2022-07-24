package com.yiyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yiyu.entity.Follow;
import com.yiyu.utils.ResponseResult;
import com.yiyu.vo.front.FollowVo;

/**
 * (Follow)表服务接口
 *
 * @author makejava
 * @since 2022-05-17 21:03:21
 */
public interface FollowService extends IService<Follow> {

    boolean getFollow(Long toUserId);

    ResponseResult<Object> follow(FollowVo followVo);

    ResponseResult<Object> unFollow(FollowVo followVo);

    int getFollowCount(Long author);
}

