package com.yiyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yiyu.dto.back.BackArticleListDTO;
import com.yiyu.dto.front.*;
import com.yiyu.entity.Article;
import com.yiyu.utils.ResponseResult;
import com.yiyu.vo.back.BackArticleListVo;
import com.yiyu.vo.back.IdsVo;
import com.yiyu.vo.front.ArticleConditionVo;
import com.yiyu.vo.front.ArticleVo;
import com.yiyu.vo.front.HotArtCategoryVo;

import java.util.List;

/**
 * 文章表(Article)表服务接口
 *
 * @author makejava
 * @since 2022-02-14 15:48:30
 */
public interface ArticleService extends IService<Article> {

    ResponseResult<List<HotArticleDTO>> hotArtCategory(HotArtCategoryVo hotArtCategoryVo);

    ResponseResult<PageDTO<ArticleListDTO>> articleList(ArticleConditionVo conditionVo);

    ResponseResult<ArticleDetailDTO> getArticleDetail(Long id) ;

    ResponseResult<Object> incrViewCount(Long articleId);
    void viewCount(Long articleId,Long viewCount);

    ResponseResult<Object> addArticle(ArticleVo articleVo) ;

    ResponseResult<LikeDTO> basicLike(Long articleId) ;

    Long getViewCount(Long authorId);

    Long getArticleCount(Long authorId);

    ArticleDetailDTO articleDetail(Long id);

    ResponseResult<PageDTO<BackArticleListDTO>> backArticleList(BackArticleListVo backArticleListVo);

    ResponseResult<Object> articleContent(Long id);

    ResponseResult<Object> deleteArticles(IdsVo articleIds);
}

