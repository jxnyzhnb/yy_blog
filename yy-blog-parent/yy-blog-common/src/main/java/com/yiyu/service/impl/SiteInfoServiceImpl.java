package com.yiyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiyu.constants.AdminConst;
import com.yiyu.constants.ArticleConst;
import com.yiyu.constants.UserConst;
import com.yiyu.dto.back.ArticleRankDTO;
import com.yiyu.dto.back.BlogBackInfoDTO;
import com.yiyu.dto.back.UniqueViewDTO;
import com.yiyu.dto.front.ArticleDetailDTO;
import com.yiyu.dto.front.UserCategoryDTO;
import com.yiyu.entity.Article;
import com.yiyu.entity.UniqueView;
import com.yiyu.entity.User;
import com.yiyu.service.*;
import com.yiyu.utils.BeanCopyUtils;
import com.yiyu.utils.RedisCache;
import com.yiyu.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yiyu
 * @version 1.0 2022-06-02 21:30:22
 * @ClassName : com.yiyu.service.impl.SiteInfoServiceImpl
 * @Description :
 */
@Service
public class SiteInfoServiceImpl implements SiteInfoService {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UniqueViewService uniqueViewService;
    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseResult<BlogBackInfoDTO> getBlogBackInfo() {
        //1.获得独立访客访问量
        Integer viewCount = redisCache.getCacheObject(AdminConst.UNIQUE_VIEW_COUNT);
        //2.获得留言量
        int messageCount = messageService.count();
        //3.获得普通用户量
        int userCount = userService.count(new LambdaQueryWrapper<User>().eq(User::getType, UserConst.USER_TYPE_DEFAULT).eq(User::getStatus, UserConst.USER_STATUS_NORMAL));
        //4.获得文章数量
        int articleCount = articleService.count(new LambdaQueryWrapper<Article>().eq(Article::getStatus, ArticleConst.ARTICLE_STATUS_NORMAL));
        //5.分类集合
        List<UserCategoryDTO> userCategoryList=categoryService.getCategoryByName();
        //6.一周UV数量
        List<UniqueView> uniqueViews = uniqueViewService.daysUniqueViewCount(AdminConst.UV_INTERVAL_DAYS_DEFAULT);
        //转换成dto并将日期格式转换成天数级别
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<UniqueViewDTO> uniqueViewList = uniqueViews.stream()
                .map(uniqueView -> new UniqueViewDTO().setDay(format.format(uniqueView.getCreateTime())).setViewsCount(uniqueView.getViewCount()))
                .collect(Collectors.toList());
        //7.获得文章浏览量排行
        List<ArticleRankDTO> articleRankList;
        if (redisCache.exist("article:detail")) {
            Map<String, ArticleDetailDTO> articleDetailMap = redisCache.getCacheMap("article:detail");
            articleRankList = articleDetailMap.keySet()
                    .stream()
                    .map(articleDetailMap::get)
                    .sorted((o1, o2) -> (int) (o2.getViewCount()-o1.getViewCount()))
                    .map(articleDetailDTO -> BeanCopyUtils.copyBean(articleDetailDTO, ArticleRankDTO.class))
                    .limit(AdminConst.ADMIN_ARTICLE_RANK_COUNT)
                    .collect(Collectors.toList());
        } else {
            articleRankList = articleService.list(new LambdaQueryWrapper<Article>()
                    .eq(Article::getStatus, ArticleConst.ARTICLE_STATUS_DEFAULT).select(Article::getTitle, Article::getViewCount))
                    .stream()
                    .map(article -> article.setNickName(userService.lambdaQuery().eq(User::getId, article.getCreateBy()).select(User::getNickName).one().getNickName()))
                    .map(article -> BeanCopyUtils.copyBean(article, ArticleRankDTO.class))
                    .collect(Collectors.toList());
        }
        BlogBackInfoDTO blogBackInfoDTO = BlogBackInfoDTO.builder()
                .viewsCount(viewCount)
                .messageCount(messageCount)
                .articleCount(articleCount)
                .articleRankDTOList(articleRankList)
                .categoryDTOList(userCategoryList)
                .uniqueViewDTOList(uniqueViewList)
                .userCount(userCount).build();

        return ResponseResult.okResult(blogBackInfoDTO);
    }
}
