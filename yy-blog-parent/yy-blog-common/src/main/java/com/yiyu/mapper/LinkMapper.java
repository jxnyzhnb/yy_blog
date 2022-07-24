package com.yiyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiyu.entity.Link;
import org.apache.ibatis.annotations.Mapper;

/**
 * 友链(Link)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-14 15:47:49
 */
@Mapper
public interface LinkMapper extends BaseMapper<Link> {

}

