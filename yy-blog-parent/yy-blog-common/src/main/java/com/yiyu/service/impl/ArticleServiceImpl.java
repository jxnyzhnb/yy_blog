package com.yiyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiyu.constants.ArticleConst;
import com.yiyu.constants.NoticeConst;
import com.yiyu.dto.back.BackArticleListDTO;
import com.yiyu.dto.front.*;
import com.yiyu.entity.*;
import com.yiyu.event.EventProducer;
import com.yiyu.exception.SystemException;
import com.yiyu.mapper.ArticleMapper;
import com.yiyu.mapper.TagsMapper;
import com.yiyu.service.ArticleService;
import com.yiyu.service.CategoryService;
import com.yiyu.service.LikeService;
import com.yiyu.service.UserService;
import com.yiyu.utils.*;
import com.yiyu.vo.back.BackArticleListVo;
import com.yiyu.vo.back.IdsVo;
import com.yiyu.vo.front.ArticleConditionVo;
import com.yiyu.vo.front.ArticleVo;
import com.yiyu.vo.front.HotArtCategoryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2022-02-14 16:00:36
 */
@Slf4j
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private UserService userService;
    @Autowired
    private TagsMapper tagsMapper;
    @Autowired
    private LikeService likeService;
    @Autowired
    private ElasticSearchService elasticSearchService;
    @Autowired
    private EventProducer producer;

    @Override
    public ResponseResult<List<HotArticleDTO>> hotArtCategory(HotArtCategoryVo hotArticleDTO) {
        Integer type = hotArticleDTO.getType();
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //查看登录用户热门列表
        if (type.equals(ArticleConst.HOT_ARTICLE_TYPE_SELF)) {
            Long userId = SecurityUtils.getUserId();
            queryWrapper.eq(Article::getCreateBy, userId);
        } else if (type.equals(ArticleConst.HOT_ARTICLE_TYPE_OTHER)) {
            //查看别人主页热门列表
            queryWrapper.eq(Article::getCreateBy, hotArticleDTO.getWid());
        } else if (type.equals(ArticleConst.HOT_ARTICLE_TYPE_DETAIL)) {
            //查看文章详情热门列表
            Long authorId = hotArticleDTO.getAid();
            Article article = getById(authorId);
            if (Objects.nonNull(article)) {
                queryWrapper.eq(Article::getCreateBy, article.getCreateBy());
            } else {
                throw new SystemException(HttpCodeEnum.PARAMETER_ERROR);
            }
        }
        return getHotArticle(queryWrapper);

    }

    private ResponseResult<List<HotArticleDTO>> getHotArticle(LambdaQueryWrapper<Article> queryWrapper) {
        //不是草稿的博客
        queryWrapper.eq(Article::getStatus, ArticleConst.ARTICLE_STATUS_NORMAL);
        //根据浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        Page<Article> page = new Page<>(ArticleConst.ARTICLE_HOT_CURRENT_PAGE, ArticleConst.ARTICLE_HOT_COUNT);
        page(page, queryWrapper);
        List<Article> hotArticleList = page.getRecords();
        hotArticleList = hotArticleList.stream()
                .filter(article -> Objects.nonNull(redisCache.getCacheMap("article:viewCount", article.getId().toString())))
                .map(article -> article.setViewCount(Long.valueOf((int) redisCache.getCacheMap("article:viewCount", article.getId().toString()))))
                .collect(Collectors.toList());

        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(hotArticleList, HotArticleDTO.class));
    }

    @Override
    public ResponseResult<PageDTO<ArticleListDTO>> articleList(ArticleConditionVo conditionVo) {
        //如果pageNum或pageSize值为空就报500
        if (Objects.isNull(conditionVo.getPageNum()) || Objects.isNull(conditionVo.getPageSize())) {
            return ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR);
        }
        //如果关键字不为null或空就走es搜索
        if (Objects.nonNull(conditionVo.getQ()) && !"".equals(conditionVo.getQ())) {
            return elasticSearchService.searchArticle(conditionVo.getQ(), conditionVo.getPageNum() - 1, conditionVo.getPageSize());
        } else {
            LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
            //获取查询类型
            Integer type = conditionVo.getType();
            //如果是查询别人的文章列表
            if (type.equals(ArticleConst.ARTICLE_QUERY_TYPE_OTHER)) {
                //获取需要查询的用户id
                Long userId = conditionVo.getWid();
                if (Objects.nonNull(userId)) {
                    queryWrapper.eq(Article::getCreateBy, userId);
                    queryWrapper.orderByDesc(Article::getIsTop);
                } else {
                    return ResponseResult.errorResult(HttpCodeEnum.PARAMETER_ERROR);
                }
            } else if (type.equals(ArticleConst.ARTICLE_QUERY_TYPE_SELF)) {
                Long userId = SecurityUtils.getUserId();
                queryWrapper.eq(Article::getCreateBy, userId);
                queryWrapper.orderByDesc(Article::getIsTop);
            }

            return getArticle(conditionVo, queryWrapper);
        }
    }

    private ResponseResult<PageDTO<ArticleListDTO>> getArticle(ArticleConditionVo conditionVo, LambdaQueryWrapper<Article> queryWrapper) {
        //判断分类id是否为null并大于0
        queryWrapper.eq(Objects.nonNull(conditionVo.getCategoryId()) && conditionVo.getCategoryId() > 0, Article::getCategoryId, conditionVo.getCategoryId());
        //文章是正式发布的
        queryWrapper.eq(Article::getStatus, ArticleConst.ARTICLE_STATUS_NORMAL);
        //将置顶的文章放在第一位即isTop为1,根据时间逆序排序
        queryWrapper.orderByDesc(Article::getCreateTime);
        //进行分页查询
        Page<Article> page = new Page<>(conditionVo.getPageNum(), conditionVo.getPageSize());
        page(page, queryWrapper);
        List<Article> articles = page.getRecords();
        articles = articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName())
                        .setNickName(userService.getById(article.getCreateBy()).getNickName()))
                .collect(Collectors.toList());
        //转换为Vo
        List<ArticleListDTO> articleLists = BeanCopyUtils.copyBeanList(articles, ArticleListDTO.class);
        PageDTO<ArticleListDTO> pages = new PageDTO<>(articleLists, page.getTotal());
        return ResponseResult.okResult(pages);
    }

    @Override
    public ResponseResult<ArticleDetailDTO> getArticleDetail(Long id) {
        ArticleDetailDTO articleDetails = redisCache.getCacheMap("article:detail", id.toString());
        //如果在redis中找不到该文章的缓存就从数据库中查询;否则直接返回
        if (Objects.isNull(articleDetails)) {
            articleDetails = articleDetail(id);
        } else {
            //只更新浏览量
            articleDetails.setViewCount(Long.valueOf((int) redisCache.getCacheMap("article:viewCount", id.toString())));
            articleDetails.setLikeCount(getById(id).getLikeCount());
        }
        return ResponseResult.okResult(articleDetails);
    }

    @Override
    public ResponseResult<Object> incrViewCount(Long articleId) {
        //访问数加1
        redisCache.incrMapKey("article:viewCount", articleId.toString(), 1L);
        return ResponseResult.okResult();
    }

    @Override
    public void viewCount(Long articleId, Long viewCount) {
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        //根据id查询文章信息
        Article article = getById(articleId);
        //访问量加1
        article.setViewCount(viewCount);
        //更新信息
        updateWrapper.eq(Article::getId, articleId);
        updateWrapper.set(Article::getViewCount, viewCount);
        update(updateWrapper);
    }

    @Transactional
    @Override
    public ResponseResult<Object> addArticle(ArticleVo articleVo) {
        //获得tagid
        Long[] tagsIds = articleVo.getTagsIds();
        //判断分类id是否为null
        if (Objects.isNull(articleVo.getCategoryId())) {
            //添加分类
            Category category = new Category();
            category.setName(articleVo.getCategoryName());
            categoryService.save(category);
            //为文章vo更新categoryId
            articleVo.setCategoryId(category.getId());
        }
        Article article = BeanCopyUtils.copyBean(articleVo, Article.class);
        //保存文章
        save(article);
        //通过流新建ArticleTags对象并插入
        List<ArticleTags> articleTagsList = Arrays.stream(tagsIds)
                .map(tagsId -> new ArticleTags(article.getId(), tagsId))
                .collect(Collectors.toList());
        tagsMapper.insertArticleTags(articleTagsList);
        //将该文章的viewCount监控加入到redis中
        redisCache.setCacheMap("article:viewCount", article.getId().toString(), 0);
        //消费发博文事件,将博文存储到es中
        Event event = new Event();
        event.setTopic(NoticeConst.TOPIC_PUBLISH_ARTICLE);
        //存储添加的文章id作为事件的发送id
        event.setSendId(article.getId());
        producer.fireEvent(event);
        return ResponseResult.okResult(article.getId());
    }

    @Override
    public ResponseResult<LikeDTO> basicLike(Long articleId) {
        //查询该文章的作者
        Article article = getById(articleId);
        Long authorId = article.getCreateBy();
        User user = userService.getById(authorId);
        //封装成dto
        LikeDTO likeDTO = BeanCopyUtils.copyBean(user, LikeDTO.class);
        //设置点赞人数
        likeDTO.setLikeCount(article.getLikeCount());
        return ResponseResult.okResult(likeDTO);
    }

    @Override
    public Long getViewCount(Long authorId) {
        long count;
        try {
            //从redis中获取数据
            LambdaQueryWrapper<Article> query = new LambdaQueryWrapper<>();
            query.eq(Article::getCreateBy, authorId)
                    .select(Article::getId);
            List<Object> articleIds = listObjs(query);
            count = articleIds.stream()
                    .mapToInt(articleId -> redisCache.getCacheMap("article:viewCount", articleId.toString()))
                    .sum();
        } catch (Exception e) {
            //如果出现异常就从数据库中查询数据
            e.printStackTrace();
            LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Article::getCreateBy, authorId)
                    .select(Article::getViewCount);
            count = count(queryWrapper);
        }

        return count;
    }

    @Override
    public Long getArticleCount(Long authorId) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getCreateBy, authorId);
        return (long) count(queryWrapper);
    }

    @Override
    public ArticleDetailDTO articleDetail(Long id) {
        //System.out.println("走db+++++++++++++++++++++++++");
        //通过文章id查询文章信息
        Article article = getById(id);
        //得到文章标签数组
        List<String> tags = tagsMapper.getTagsByArticleId(id);
        //封装为vo
        ArticleDetailDTO articleDetails = BeanCopyUtils.copyBean(article, ArticleDetailDTO.class);
        User author = userService.getById(article.getCreateBy());
        //设置作者名字
        if (Objects.nonNull(author)) {
            articleDetails.setNickName(author.getNickName());
        }
        //查询文章分类名
        Long categoryId = article.getCategoryId();
        Category category = categoryService.getById(categoryId);
        //防止category.getName()空指针异常
        if (Objects.nonNull(category)) {
            articleDetails.setCategoryName(category.getName());
        }
        Integer viewCount = redisCache.getCacheMap("article:viewCount", article.getId().toString());
        if (Objects.nonNull(viewCount)) {
            articleDetails.setViewCount(Long.valueOf(viewCount));
        }
        //设置文章标签
        articleDetails.setTags(tags);
        //存放到redis中
        redisCache.setCacheMap("article:detail", id.toString(), articleDetails);
        return articleDetails;
    }

    @Override
    public ResponseResult<PageDTO<BackArticleListDTO>> backArticleList(BackArticleListVo backArticleListVo) {
        //进行分页
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //关键字搜索文章标题
        if (Objects.nonNull(backArticleListVo.getQ())){
            queryWrapper.like(Article::getTitle,backArticleListVo.getQ());
        }
        Page<Article> page = new Page<>(backArticleListVo.getPageNum(), backArticleListVo.getPageSize());
        page(page,queryWrapper.eq(Article::getStatus,backArticleListVo.getType()));
        //取数据
        List<Article> list = page.getRecords();
        //获得文章作者
        list.forEach(article -> article.setNickName(userService.lambdaQuery().eq(User::getId,article.getCreateBy()).select(User::getNickName).one().getNickName()));
        //封装数据
        List<BackArticleListDTO> articleLists = BeanCopyUtils.copyBeanList(list, BackArticleListDTO.class);
        articleLists.forEach(article ->
                article.setCategoryName(categoryService.lambdaQuery().eq(Category::getId, article.getCategoryId()).select(Category::getName).one().getName())
                        .setTags(tagsMapper.getTagsByArticleId(article.getId())));
        PageDTO<BackArticleListDTO> pageDTO=new PageDTO<>(articleLists,page.getTotal());
        return ResponseResult.okResult(pageDTO);
    }

    @Override
    public ResponseResult<Object> articleContent(Long id) {
        Article article = getOne(new LambdaQueryWrapper<Article>().eq(Article::getId, id).select(Article::getContent));
        return ResponseResult.okResult(article.getContent());
    }

    @Override
    public ResponseResult<Object> deleteArticles(IdsVo articleIds) {
        removeByIds(articleIds.getIds());
        return ResponseResult.okResult();
    }


}

