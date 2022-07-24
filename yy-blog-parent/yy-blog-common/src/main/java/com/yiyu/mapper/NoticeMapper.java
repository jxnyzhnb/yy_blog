package com.yiyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiyu.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Notice)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-01 16:40:21
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
    Integer countByIsRead(@Param("toId") Long toId,
                      @Param("isRead") String isRead,
                      @Param("tType") String tType);


    List<Notice> getLetterList(@Param("fromId") Long fromId,
                               @Param("toId") Long toId,
                               @Param("tType")String tType);
    Notice getNewestLetter(@Param("fromId") Long fromId,
                                 @Param("toId")Long toId,
                                 @Param("tType")String tType);
}

