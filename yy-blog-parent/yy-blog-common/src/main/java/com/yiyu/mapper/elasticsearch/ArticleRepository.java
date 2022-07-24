package com.yiyu.mapper.elasticsearch;

import com.yiyu.entity.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
//Article表示该类联系的类,Long代表该类主键的类型
public interface ArticleRepository extends ElasticsearchRepository<Article,Long> {
}


