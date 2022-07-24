package com.yiyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiyu.entity.UniqueView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (UniqueView)表数据库访问层
 *
 * @author makejava
 * @since 2022-06-03 19:05:22
 */
@Mapper
public interface UniqueViewMapper extends BaseMapper<UniqueView> {

    List<UniqueView> getDaysUniqueCount(@Param("endTime") String endTime,
                                        @Param("startTime") String startTime);
}

