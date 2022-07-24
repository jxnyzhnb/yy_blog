package com.yiyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiyu.entity.Tags;
import org.apache.ibatis.annotations.Mapper;
import com.yiyu.entity.ArticleTags;

import java.util.List;

/**
 * (Tags)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-03 20:13:52
 */
@Mapper
public interface TagsMapper extends BaseMapper<Tags> {
    List<String> getTagsByArticleId(Long articleId);
    void insertArticleTags(List<ArticleTags> articleTagsList);
}

