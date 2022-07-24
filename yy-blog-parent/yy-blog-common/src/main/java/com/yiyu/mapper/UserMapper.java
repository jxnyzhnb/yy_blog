package com.yiyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiyu.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-14 15:47:49
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}

