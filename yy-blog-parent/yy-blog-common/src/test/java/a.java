import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiyu.constants.AdminConst;
import com.yiyu.constants.NoticeConst;
import com.yiyu.dto.back.ArticleRankDTO;
import com.yiyu.dto.front.ArticleDetailDTO;
import com.yiyu.dto.front.UserCategoryDTO;
import com.yiyu.entity.Article;
import com.yiyu.entity.UniqueView;
import com.yiyu.service.*;
import com.yiyu.utils.BeanCopyUtils;
import com.yiyu.utils.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import com.yiyu.YyBlogCommonApplication;
import com.yiyu.entity.Notice;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zh
 * @ClassName : PACKAGE_NAME.a
 * @Description :
 * Created by user on 2022-05-12 08:51:23
 * Copyright  2020 user. All rights reserved.
 */
@SpringBootTest
@ContextConfiguration(classes = YyBlogCommonApplication.class)
public class a {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private UniqueViewService uniqueViewService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleService articleService;
   /* @Value("${third.github.client_id}")
    private String GITHUB_CLIENT_ID;*/
    @Test
    public void a(){
        LambdaQueryWrapper<Notice> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Notice::getToId,1)
                .eq(Notice::getTType, NoticeConst.TYPE_LETTER)
                .select(Notice::getFromId);
        System.out.println(noticeService.listObjs(queryWrapper));

    }
    @Test
    public void b(){

        // 定义项目进行天数
        long Days = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


            long time = new Date().getTime();
            long time1 = new Date()
                    .getTime()-(60*60*48*1000);
            System.out.println(time);
            System.out.println(time1);
            Days = (int) ((time -time1) /(24
                    * 60 * 60 * 1000));
            System.out.println(Days);



    }
    @Test
    public void  c(){
        menuService.getUserMenu();
        System.out.println(5.3%2);
    }
    @Test
    public void  d(){
        List<UniqueView> uniqueViews = uniqueViewService.daysUniqueViewCount(1);
        System.out.println(uniqueViews);
    }
    @Test
    public void  e(){
        List<UserCategoryDTO> categoryByName = categoryService.getCategoryByName();
        System.out.println(categoryByName);
        Object obj = articleService.getOne(new LambdaQueryWrapper<Article>().eq(Article::getId, 1).select(Article::getContent));
        System.out.println(obj);
    }
    @Test
    public void f(){
        Map<String, ArticleDetailDTO> articleDetailMap = redisCache.getCacheMap("article:detail");
      List<ArticleRankDTO>  articleRankList = articleDetailMap.keySet()
                .stream()
                .map(articleDetailMap::get)
                .sorted(Comparator.comparing(ArticleDetailDTO::getViewCount))
                .map(articleDetailDTO -> BeanCopyUtils.copyBean(articleDetailDTO, ArticleRankDTO.class))
                .limit(AdminConst.ADMIN_ARTICLE_RANK_COUNT)
                .collect(Collectors.toList());
        System.out.println(articleRankList);
    }
}
