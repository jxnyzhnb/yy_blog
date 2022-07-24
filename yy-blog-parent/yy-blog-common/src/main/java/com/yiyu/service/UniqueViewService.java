package com.yiyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yiyu.entity.UniqueView;

import java.util.List;

/**
 * (UniqueView)表服务接口
 *
 * @author makejava
 * @since 2022-06-03 19:05:22
 */
public interface UniqueViewService extends IService<UniqueView> {

    List<UniqueView> daysUniqueViewCount(int intervalDays);

}

