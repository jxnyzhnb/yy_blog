import com.yiyu.service.impl.ElasticSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.ContextConfiguration;
import com.yiyu.YyBlogCommonApplication;
import com.yiyu.mapper.ArticleMapper;
import com.yiyu.mapper.elasticsearch.ArticleRepository;
import com.yiyu.service.ArticleService;


@SpringBootTest
@ContextConfiguration(classes = YyBlogCommonApplication.class)
public class ElasticSearchTests {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ElasticSearchService elasticSearchService;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate; // 注意不要注入ElasticsearchTemplate

    @Test
    public void testInsert() {
        articleRepository.save(articleService.getById(1));
        articleRepository.save(articleService.getById(36));
        articleRepository.save(articleService.getById(38));
        articleRepository.save(articleService.getById(39));

    }

    @Test
    public void testInsertList() {
//        articleRepository.deleteAll();
  articleRepository.saveAll(articleMapper.selectList(null));
    }
    @Test
    public void testUpdateOne() {
        articleRepository.deleteAll();
//       elasticSearchService.updateViewCount(1L,1L);
    }
/*

    @Test
    public void testUpdate() {
        DiscussPost post = discussPostMapper.selectDiscussPostById(231);
        post.setContent("我是新人,使劲灌水.");
        discussPostRepository.save(post);
    }
*/

    @Test
    public void testDelete() {
        //discussPostRepository.deleteById(231);
        articleRepository.deleteById(39L);
    }

  /*  @Test
    public void testSearchByRepository() {
         NativeSearchQuery searchQuery =  new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery("互联网寒冬", "title", "content"))
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(0, 10))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                ).build();

        Page<DiscussPost> page = discussPostRepository.search(searchQuery);
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
        System.out.println(page.getNumber());
        System.out.println(page.getSize());
        for (DiscussPost post : page) {
            System.out.println(post);
        }
    }



    @Test
    public void testSearchByTemplate(){
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery("互联网寒冬", "title", "content"))
                 .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                 .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                 .withPageable(PageRequest.of(0, 10))
                .withHighlightFields(
                         new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                         new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                 ).build();

        //已过期
        //elasticsearchRestTemplate.queryForPage(searchQuery, DiscussPost.class, new SearchRe)

         SearchHits<DiscussPost> search = elasticsearchRestTemplate.search(searchQuery, DiscussPost.class);
        // 得到查询结果返回的内容
        List<SearchHit<DiscussPost>> searchHits = search.getSearchHits();
        // 设置一个需要返回的实体类集合
        List<DiscussPost> discussPosts = new ArrayList<>();
         // 遍历返回的内容进行处理
         for(SearchHit<DiscussPost> searchHit : searchHits){
              // 高亮的内容
             Map<String, List<String>> highLightFields = searchHit.getHighlightFields();
             // 将高亮的内容填充到content中
             searchHit.getContent().setTitle(
                     highLightFields.get("title") == null ? searchHit.getContent().getTitle() : highLightFields.get("title").get(0)
             );
             searchHit.getContent().setTitle(
                     highLightFields.get("content") == null ? searchHit.getContent().getContent() : highLightFields.get("content").get(0))
             ;
             // 放到实体类中
             discussPosts.add(searchHit.getContent());
          }
          System.out.println(discussPosts.size());
         for(DiscussPost discussPost : discussPosts){
            System.out.println(discussPost);
        }
     }*/

 }

