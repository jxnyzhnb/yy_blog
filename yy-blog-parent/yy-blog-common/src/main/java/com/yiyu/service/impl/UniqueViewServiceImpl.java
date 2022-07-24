package com.yiyu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiyu.constants.AdminConst;
import com.yiyu.mapper.UniqueViewMapper;
import com.yiyu.entity.UniqueView;
import com.yiyu.service.UniqueViewService;
import com.yiyu.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (UniqueView)表服务实现类
 *
 * @author makejava
 * @since 2022-06-03 19:05:22
 */
@Service("uniqueViewService")
public class UniqueViewServiceImpl extends ServiceImpl<UniqueViewMapper, UniqueView> implements UniqueViewService {
    @Autowired
    private UniqueViewMapper uniqueViewMapper;
    @Override
    public List<UniqueView> daysUniqueViewCount(int intervalDays) {
        //获得下一天的凌晨时间戳
        String nextDayDate = DateUtils.getDayDateString(AdminConst.UV_INTERVAL_DAYS_ZERO);
        //获得七天前的凌晨时间戳
        String beforeDayDate = DateUtils.getDayDateString(intervalDays);
        return uniqueViewMapper.getDaysUniqueCount(nextDayDate,beforeDayDate);
    }
}

