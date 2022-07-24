package com.yiyu.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiyu.dto.front.ArticleDetailDTO;
import com.yiyu.entity.Discuss;
import com.yiyu.service.DiscussService;
import com.yiyu.service.impl.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.yiyu.entity.Article;
import com.yiyu.service.ArticleService;
import com.yiyu.utils.RedisCache;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * @author zh
 * @ClassName : zh.nb.job.ScheduleJob
 * @Description :
 * Created by user on 2022-04-06 16:31:11
 * Copyright  2020 user. All rights reserved.
 */
@Component
public class ScheduleJob implements CommandLineRunner {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ElasticSearchService elasticSearchService;
    @Autowired
    private DiscussService discussService;

    //每隔5分钟更新数据库中的文章和帖子浏览量
    @Scheduled(cron = "0 0/5 * * * ?")
    public void updateViewCountDataB(){
        Map<String, Integer> articleViewCount = redisCache.getCacheMap("article:viewCount");
        for (Map.Entry<String, Integer> entry : articleViewCount.entrySet()) {
            articleService.viewCount(Long.valueOf(entry.getKey()), entry.getValue().longValue());
        }
        Map<String, Integer> discussViewCount = redisCache.getCacheMap("discuss:viewCount");
        for (Map.Entry<String, Integer> entry : discussViewCount.entrySet()) {
            discussService.viewCount(Long.valueOf(entry.getKey()), entry.getValue());
        }
    }
    //每隔6分钟更新es中的文章浏览量
    @Scheduled(cron = "0 0/6 * * * ?")
    public void updateViewCountEs(){
        Map<String, Integer> articleViewCount = redisCache.getCacheMap("article:viewCount");
        for (Map.Entry<String, Integer> entry : articleViewCount.entrySet()) {
            elasticSearchService.updateViewCount(Long.valueOf(entry.getKey()), entry.getValue().longValue());
        }
    }
    //每隔5分钟更新redis中的文章详情
    @Scheduled(cron = "0 0/5 * * * ?")
    public void updateArticleDetail(){
        Map<String, Integer> articleViewCount = redisCache.getCacheMap("article:viewCount");
        for (Map.Entry<String, Integer> entry : articleViewCount.entrySet()) {
//            redisCache.getCacheMap(Long.valueOf(entry.getKey()), entry.getValue().longValue());
            ArticleDetailDTO articleDetail = redisCache.getCacheMap("article:detail", entry.getKey());
            articleDetail.setViewCount(entry.getValue().longValue());
            redisCache.setCacheMap("article:detail",entry.getKey(),articleDetail);
        }
    }
    //启动时执行
    @Override
    public void run(String... args) throws Exception {
        //1.将文章浏览量和详情存储到redis中
        List<Article> list = articleService.list();
        //要用Integer类型进行存储ViewCount,因为如果用Long,那么取出来的类型就是string类型
        Map<String,Integer> map=new HashMap<>();
        list.forEach(article->map.put(article.getId().toString(), article.getViewCount().intValue()));
        redisCache.setAllCacheMap("article:viewCount",map);
        //2.将文章详情存入redis
        articleService.list(new LambdaQueryWrapper<Article>().select(Article::getId))
                .forEach(article->articleService.articleDetail(article.getId()));
        //3.将帖子浏览量存redis
        List<Discuss> disList = discussService.list(new LambdaQueryWrapper<Discuss>().select(Discuss::getId,Discuss::getViewCount));
        Map<String,Integer> disMap=new HashMap<>();
        disList.forEach(discuss->disMap.put(discuss.getId().toString(), discuss.getViewCount()));
        redisCache.setAllCacheMap("discuss:viewCount",disMap);
    }
}
