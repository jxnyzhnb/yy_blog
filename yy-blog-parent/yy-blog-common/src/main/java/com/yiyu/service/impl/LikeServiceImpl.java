package com.yiyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiyu.constants.NoticeConst;
import com.yiyu.entity.*;
import com.yiyu.event.EventProducer;
import com.yiyu.mapper.LikeMapper;
import com.yiyu.service.*;
import com.yiyu.utils.BeanCopyUtils;
import com.yiyu.utils.ResponseResult;
import com.yiyu.utils.SecurityUtils;
import com.yiyu.vo.front.LikeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * (Like)表服务实现类
 *
 * @author makejava
 * @since 2022-05-06 10:54:54
 */
@Slf4j
@Service("likeService")
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements LikeService {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private EventProducer producer;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private DiscussService discussService;

    @Transactional
    @Override
    public ResponseResult<Object> setLike(LikeVo likeVo) {
        //1.获取登录用户id
        Like like = BeanCopyUtils.copyBean(likeVo, Like.class);
        //插入
        save(like);
        //发送通知
        sendLike(like, likeVo);
        return ResponseResult.okResult();
    }

    private void sendLike(Like like, LikeVo likeVo) {
        Event event = new Event();
        Long userId = SecurityUtils.getUserId();
        //事件为评论
        event.setEventType("like");
        Map<String, Object> map = new HashMap<>();
        //点赞类型为1，表示是博文点赞
        if (NoticeConst.LIKE_TYPE_ARTICLE.equals(likeVo.getType())) {
            //3.增加文章点赞数
            //3.1获取文章点赞数
            Article article = articleService.getById(likeVo.getTypeId());
            Long createBy = article.getCreateBy();

            Integer likeCount = article.getLikeCount();
            //更新点赞
            LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Article::getId, likeVo.getTypeId());
            updateWrapper.set(Article::getLikeCount, likeCount + 1);
            articleService.update(updateWrapper);
            //自己点赞自己的不发通知
            if (createBy.equals(userId)) {
                return;
            }
            //发通知
            event.setTopic(NoticeConst.TOPIC_LIKE_ARTICLE)
                    .setReceiveId(createBy);
            map.put("articleId", likeVo.getTypeId());
        }//帖子点赞
        else if (NoticeConst.LIKE_TYPE_DISCUSS.equals(likeVo.getType())) {
            Discuss discuss = discussService.getById(likeVo.getTypeId());
            Long createBy = discuss.getCreateBy();

            Integer likeCount = discuss.getLikeCount();
            //更新点赞
            LambdaUpdateWrapper<Discuss> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Discuss::getId, likeVo.getTypeId());
            updateWrapper.set(Discuss::getLikeCount, likeCount + 1);
            discussService.update(updateWrapper);
            //自己点赞自己的不发通知
            if (createBy.equals(userId)) {
                return;
            }
            //发通知
            event.setTopic(NoticeConst.TOPIC_LIKE_DISCUSS)
                    .setReceiveId(createBy);
            map.put("discussId", likeVo.getTypeId());
            //评论帖子或博文
        } else if (NoticeConst.LIKE_TYPE_COMMENT_ARTICLE.equals(likeVo.getType()) || NoticeConst.LIKE_TYPE_COMMENT_DISCUSS.equals(likeVo.getType())) {
            Comment comment = commentService.getById(likeVo.getTypeId());
            //自己点赞自己的不发通知
            Long createBy = comment.getCreateBy();

            Integer likeCount = comment.getLikeCount();
            //更新点赞
            LambdaUpdateWrapper<Comment> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Comment::getId, likeVo.getTypeId());
            updateWrapper.set(Comment::getLikeCount, likeCount + 1);
            commentService.update(updateWrapper);
            if (createBy.equals(userId)) {
                return;
            }
            if (NoticeConst.LIKE_TYPE_COMMENT_ARTICLE.equals(likeVo.getType())) {
                event.setTopic(NoticeConst.TOPIC_LIKE_ARTICLE_COMMENT);
                Long articleId = comment.getTypeId();
                map.put("articleId", articleId);
            } else if (NoticeConst.LIKE_TYPE_COMMENT_DISCUSS.equals(likeVo.getType())) {
                event.setTopic(NoticeConst.TOPIC_LIKE_DISCUSS_COMMENT);
                Long discussId = comment.getTypeId();
                map.put("discussId", discussId);
            }
            event.setReceiveId(createBy);
            map.put("commentId", likeVo.getTypeId());
        }

        map.put("likeId", like.getId());
        event.setData(map);
        //设置发送者id
        event.setSendId(userId);
        producer.fireEvent(event);
    }

    @Transactional
    @Override
    public ResponseResult<Object> unLike(LikeVo likeVo) {
        //1.获取登录用户id
        Long userId = SecurityUtils.getUserId();
        Integer likeCount;
        //2.查询到该条点赞记录并删除
        LambdaQueryWrapper<Like> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Like::getTypeId, likeVo.getTypeId());
        queryWrapper.eq(Like::getCreateBy, userId);
        Like one = getOne(queryWrapper);
        //删除点赞通知
        noticeService.delete(one.getNoticeId());
        //删除点赞记录
        remove(queryWrapper);
        if (NoticeConst.LIKE_TYPE_ARTICLE.equals(likeVo.getType())) {
            //2.获取当前文章点赞数
            Article article = articleService.getById(likeVo.getTypeId());
            likeCount = article.getLikeCount();
            //3.更新文章点赞数
            if (likeCount > 0) {
                LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(Article::getId, likeVo.getTypeId());
                updateWrapper.set(Article::getLikeCount, likeCount - 1);
                articleService.update(updateWrapper);
            }
            //帖子点赞
        }  else if (NoticeConst.LIKE_TYPE_DISCUSS.equals(likeVo.getType())){
            //2.获取当前文章点赞数
            Discuss discuss = discussService.getById(likeVo.getTypeId());
            likeCount = discuss.getLikeCount();
            //3.更新评论点赞数
            if (likeCount > 0) {
                LambdaUpdateWrapper<Discuss> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(Discuss::getId, likeVo.getTypeId());
                updateWrapper.set(Discuss::getLikeCount, likeCount - 1);
                discussService.update(updateWrapper);
            }
            //帖子或文章评论点赞
        }else if (NoticeConst.LIKE_TYPE_COMMENT_ARTICLE.equals(likeVo.getType())||NoticeConst.LIKE_TYPE_COMMENT_DISCUSS.equals(likeVo.getType())) {
            //2.获取当前文章点赞数
            Comment comment = commentService.getById(likeVo.getTypeId());
            likeCount = comment.getLikeCount();
            //3.更新评论点赞数
            if (likeCount > 0) {
                LambdaUpdateWrapper<Comment> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(Comment::getId, likeVo.getTypeId());
                updateWrapper.set(Comment::getLikeCount, likeCount - 1);
                commentService.update(updateWrapper);
            }
        }
        return ResponseResult.okResult();
    }

    @Override
    public boolean isLike(Long typeId, Integer type) {
        Like one = null;
        if (SecurityUtils.isLogin()) {
            Long userId = SecurityUtils.getUserId();
            LambdaQueryWrapper<Like> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Like::getTypeId, typeId)
                    .eq(Like::getCreateBy, userId)
                    .eq(Like::getType, type);
            one = getOne(queryWrapper);

        }
        return Objects.nonNull(one);
    }

    @Override
    public int getLikeCount(Long authorId) {
        //1.获得文章点赞
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getCreateBy, authorId)
                .select(Article::getLikeCount);
        List<Object> articleLikeCounts = articleService.listObjs(articleWrapper);
        int articleLikeCount = articleLikeCounts.stream()
                .mapToInt(likeCount -> (int) likeCount).sum();
        //1.2获得用户的所有评论的总点赞
        //获得评论点赞
        LambdaQueryWrapper<Comment> commentWrapper = new LambdaQueryWrapper<>();
        commentWrapper.eq(Comment::getCreateBy, authorId)
                .select(Comment::getLikeCount);
        List<Object> commentLikeCounts = commentService.listObjs(commentWrapper);
        int commentLikeCount = commentLikeCounts.stream()
                .mapToInt(likeCount -> (int) likeCount).sum();
        log.info("评论点赞数{}", commentLikeCount);
        log.info("文章点赞数{}", articleLikeCount);
        return articleLikeCount + commentLikeCount;
    }


}

