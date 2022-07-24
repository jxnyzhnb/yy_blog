package com.yiyu.service.impl;

import com.yiyu.dto.front.ArticleListDTO;
import com.yiyu.dto.front.PageDTO;
import com.yiyu.entity.Article;
import com.yiyu.mapper.elasticsearch.ArticleRepository;
import com.yiyu.service.CategoryService;
import com.yiyu.service.UserService;
import com.yiyu.utils.BeanCopyUtils;
import com.yiyu.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zh
 * @ClassName : zh.nb.service.impl.ElasticSearchService
 * @Description :
 * Created by user on 2022-05-04 16:49:25
 * Copyright  2020 user. All rights reserved.
 */
@Slf4j
@Service
public class ElasticSearchService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public ResponseResult<PageDTO<ArticleListDTO>> searchArticle(String keyword, int pageNum, int pageSize) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyword, "title", "summary"))
                .withSort(SortBuilders.fieldSort("isTop").order(SortOrder.DESC))   // 默认按匹配度排序
                .withSort(SortBuilders.fieldSort("viewCount").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                //pageNum是从0开始的
                .withPageable(PageRequest.of(pageNum, pageSize))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em style=\"color: red\">").postTags("</em>"),
                        new HighlightBuilder.Field("summary").preTags("<em style=\"color: red\">").postTags("</em>")
                ).build();

        SearchHits<Article> search = elasticsearchRestTemplate.search(searchQuery, Article.class);
        // 得到查询结果返回的内容
        List<SearchHit<Article>> searchHits = search.getSearchHits();
        // 设置一个需要返回的实体类集合
        List<Article> articleList = new ArrayList<>();
        // 遍历返回的内容进行处理
        for (SearchHit<Article> searchHit : searchHits) {
            // 高亮的内容
            Map<String, List<String>> highLightFields = searchHit.getHighlightFields();
            // 将高亮的内容填充到content中
            searchHit.getContent().setTitle(
                    highLightFields.get("title") == null ? searchHit.getContent().getTitle() : highLightFields.get("title").get(0)
            );
            searchHit.getContent().setSummary(
                    highLightFields.get("summary") == null ? searchHit.getContent().getSummary() : highLightFields.get("summary").get(0)
            );
            // 放到实体类中
            articleList.add(searchHit.getContent());
        }
        articleList = articleList.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName())
                        .setNickName(userService.getById(article.getCreateBy()).getNickName()))
                .collect(Collectors.toList());
        List<ArticleListDTO> articleLists = BeanCopyUtils.copyBeanList(articleList, ArticleListDTO.class);
        return ResponseResult.okResult(new PageDTO<>(articleLists, search.getTotalHits()));
    }

    public void saveArticle(Article article) {
        articleRepository.save(article);
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    public void updateViewCount(Long id, Long viewCount) {
        //根据ID 修改某个字段
        Document document = Document.create();
        document.putIfAbsent("viewCount", viewCount); //更新后的内容
        UpdateQuery updateQuery = UpdateQuery.builder(String.valueOf(id))
                .withDocument(document)
                .withRetryOnConflict(5) //冲突重试
                .withDocAsUpsert(false) //不加默认false。true表示更新时不存在就插入
                .build();
        UpdateResponse response = elasticsearchRestTemplate.update(updateQuery, IndexCoordinates.of("article"));
    }
}
