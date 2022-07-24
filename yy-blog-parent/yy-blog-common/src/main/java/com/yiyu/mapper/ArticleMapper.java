package com.yiyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiyu.entity.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章表(Article)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-14 15:47:47
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}

