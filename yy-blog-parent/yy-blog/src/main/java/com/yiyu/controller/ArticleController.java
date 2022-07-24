package com.yiyu.controller;

import com.yiyu.annotation.SystemLog;
import com.yiyu.dto.front.*;
import com.yiyu.service.ArticleService;
import com.yiyu.utils.ResponseResult;
import com.yiyu.vo.front.ArticleConditionVo;
import com.yiyu.vo.front.ArticleVo;
import com.yiyu.vo.front.HotArtCategoryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 文章表(Article)表控制层
 *
 * @author makejava
 * @since 2022-02-14 16:15:09
 */
@Api(tags = "文章模块")
@RestController
@RequestMapping("/article")
public class ArticleController {
    /**
     * 服务对象
     */
    @Autowired
    private ArticleService articleService;
    @ApiOperation(value = "获取热门文章和分类专栏")
    @SystemLog(businessName = "获取热门文章")
    @GetMapping("/hotArticleList")
    public ResponseResult<List<HotArticleDTO>> hotArticleList(HotArtCategoryVo hotArtCategoryVo) {
        return articleService.hotArtCategory(hotArtCategoryVo);
    }

    @ApiOperation(value = "获取文章列表")
    @SystemLog(businessName = "获取文章列表")
    @GetMapping("/articleList")
    public ResponseResult<PageDTO<ArticleListDTO>> articleList(@Valid ArticleConditionVo conditionVo) {
        return articleService.articleList(conditionVo);
    }

    @ApiOperation(value = "获取文章内容")
    @ApiImplicitParam(name = "id", value = "文章id", required = true, dataType = "Long")
    @SystemLog(businessName = "获取文章内容")
    @GetMapping("/{id}")
    public ResponseResult<ArticleDetailDTO> getArticleDetail(@NotNull @PathVariable("id") Long id) {
        return articleService.getArticleDetail(id);
    }

    @ApiOperation(value = "更新文章浏览量")
    @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "Long")
    @SystemLog(businessName = "更新文章浏览量")
    @PutMapping("/updateViewCount/{articleId}")
    public ResponseResult<Object> updateViewCount(@NotNull @PathVariable("articleId") Long articleId) {
        return articleService.incrViewCount(articleId);
    }

    @ApiOperation(value = "创作文章")
    @SystemLog(businessName = "创作文章")
    @PostMapping("/addArticle")
    public ResponseResult<Object> addArticle(@RequestBody ArticleVo articleVo) {
        return articleService.addArticle(articleVo);
    }

    @ApiOperation(value = "查询文章点赞数量及作者头像名字")
    @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "Long")
    @SystemLog(businessName = "查询文章点赞数量及作者头像名字")
    @GetMapping("/basicLike/{id}")
    public ResponseResult<LikeDTO> basicLike(@PathVariable("id") Long articleId) {
        return articleService.basicLike(articleId);
    }
}

