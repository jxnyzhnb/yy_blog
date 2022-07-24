package com.yiyu.controller;

import com.yiyu.annotation.SystemLog;
import com.yiyu.dto.back.BackArticleListDTO;
import com.yiyu.dto.front.PageDTO;
import com.yiyu.service.ArticleService;
import com.yiyu.utils.ResponseResult;
import com.yiyu.vo.back.BackArticleListVo;
import com.yiyu.vo.back.IdsVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author yiyu
 * @version 1.0 2022-06-04 16:07:34
 * @ClassName : com.yiyu.controller.ArticleController
 * @Description :
 */
@RequestMapping("/admin")
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "后台获取文章列表")
    @SystemLog(businessName = "后台获取文章列表")
    @GetMapping("/articles")
    public ResponseResult<PageDTO<BackArticleListDTO>> articles(@Valid BackArticleListVo backArticleListVo) {
        return articleService.backArticleList(backArticleListVo);
    }

    @ApiOperation(value = "后台获取文章详情")
    @ApiImplicitParam(name = "id", value = "文章id", required = true, dataType = "Long")
    @SystemLog(businessName = "后台获取文章详情")
    @GetMapping("/articles/{id}")
    public ResponseResult<Object> articles(@PathVariable("id") Long id) {
        return articleService.articleContent(id);
    }

    @ApiOperation(value = "后台删除文章")
    @SystemLog(businessName = "后台删除文章")
    @PutMapping("/articles")
    public ResponseResult<Object> articles(@RequestBody IdsVo idsVo) {
        return articleService.deleteArticles(idsVo);
    }
}
