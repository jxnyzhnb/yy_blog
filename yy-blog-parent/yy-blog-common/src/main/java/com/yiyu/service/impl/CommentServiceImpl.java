package com.yiyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiyu.constants.CommentConst;
import com.yiyu.constants.NoticeConst;
import com.yiyu.dto.front.CommentListDTO;
import com.yiyu.dto.front.NewCommentDTO;
import com.yiyu.dto.front.PageDTO;
import com.yiyu.entity.Article;
import com.yiyu.entity.Comment;
import com.yiyu.entity.Event;
import com.yiyu.entity.User;
import com.yiyu.event.EventProducer;
import com.yiyu.exception.SystemException;
import com.yiyu.mapper.CommentMapper;
import com.yiyu.service.*;
import com.yiyu.utils.BeanCopyUtils;
import com.yiyu.utils.HttpCodeEnum;
import com.yiyu.utils.ResponseResult;
import com.yiyu.utils.SecurityUtils;
import com.yiyu.vo.front.AddCommentVo;
import com.yiyu.vo.front.CommentConditionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-02-14 16:00:38
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private UserService userService;
    @Autowired
    private EventProducer producer;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private DiscussService discussService;

    @Override
    public ResponseResult<PageDTO<CommentListDTO>> commentList(CommentConditionVo conditionVo) {
        //表示点赞类型
        final int likeType;
        //评论类型为评论博文
        if (CommentConst.ARTICLE_COMMENT_TYPE.equals(conditionVo.getType())) {
            likeType = NoticeConst.LIKE_TYPE_COMMENT_ARTICLE;
            //评论类型为评论帖子
        } else if (CommentConst.DISCUSS_COMMENT_TYPE.equals(conditionVo.getType())) {
            likeType = NoticeConst.LIKE_TYPE_COMMENT_DISCUSS;
        } else {
            likeType = -1;
        }
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //评论类型
        queryWrapper.eq(Comment::getType, conditionVo.getType());
        //对应评论类型id
        queryWrapper.eq(Comment::getTypeId, conditionVo.getTypeId());
        //查询所有根评论
        queryWrapper.eq(Comment::getRootId, CommentConst.COMMENT_IS_ROOT);
        //进行分页
        Page<Comment> page = new Page<>(conditionVo.getPageNum(), conditionVo.getPageSize());
        page(page, queryWrapper);
        List<Comment> commentList = page.getRecords();
        //转换为vo
        List<CommentListDTO> commentLists = toCommentDTOList(commentList, likeType);
        commentLists = commentLists.stream()
                .map(commentListVo -> commentListVo.setChildren(getChildren(commentListVo.getId(),likeType))
                        .setIsLike(likeService.isLike(commentListVo.getId(), likeType)))
                .collect(Collectors.toList());
        return ResponseResult.okResult(new PageDTO<>(commentLists, page.getTotal()));
    }

    @Override
    public ResponseResult<Object> addComment(AddCommentVo addCommentVo) {
        Comment comment = BeanCopyUtils.copyBean(addCommentVo, Comment.class);
        save(comment);
        Long userId = SecurityUtils.getUserId();
        Long toCommentUserId = addCommentVo.getToCommentUserId();
        //如果是自己回复自己就不通知
        if (userId.equals(toCommentUserId)) {
            return ResponseResult.okResult();
        }
        //如果是文章评论
        if (CommentConst.ARTICLE_COMMENT_TYPE.equals(addCommentVo.getType())) {
            sendArticleComment(comment, addCommentVo);
        } else if (CommentConst.DISCUSS_COMMENT_TYPE.equals(addCommentVo.getType())) {
            sendDiscussComment(comment, addCommentVo);
        } else {
            throw new SystemException(HttpCodeEnum.PARAMETER_ERROR);
        }
        return ResponseResult.okResult();
    }

    private void sendDiscussComment(Comment comment, AddCommentVo addCommentVo) {

        Event event = new Event();
        //事件为评论
        event.setEventType(NoticeConst.T_TYPE_COMMENT);
        Map<String, Object> map = new HashMap<>();
        //是根评论
        if (!addCommentVo.getRootId().equals(CommentConst.COMMENT_IS_ROOT)) {
            event.setTopic(NoticeConst.TOPIC_REPLY_DICUSS_COMMENT)
                    .setReceiveId(addCommentVo.getToCommentUserId());
            map.put("toCommentId", addCommentVo.getToCommentId());
        }else {
            //通知为评论博文类型
            event.setTopic(NoticeConst.TOPIC_COMMENT_DISCUSS)
                    //评论为博文评论
                    //接收者就是写当前博文的作者
                    .setReceiveId(discussService.getById(addCommentVo.getTypeId()).getCreateBy());
        }

                //评论为回复评论
                //设置接收者为当前登录用户回复的评论的创建者
        map.put("discussId", addCommentVo.getTypeId());
        map.put("commentId", comment.getId());
        event.setData(map);
        //设置发送者id
        event.setSendId(SecurityUtils.getUserId());
        producer.fireEvent(event);
    }

    private void sendArticleComment(Comment comment, AddCommentVo addCommentVo) {
        Event event = new Event();
        //事件为评论
        event.setEventType(NoticeConst.T_TYPE_COMMENT);
        Map<String, Object> map = new HashMap<>();
        //如果rootId不为-1,说明是回复评论,设置通知主题
        if (!addCommentVo.getRootId().equals(CommentConst.COMMENT_IS_ROOT)) {
            event.setTopic(NoticeConst.TOPIC_REPLY_ARTICLE_COMMENT)
                    //评论为回复评论
                    //设置接收者为当前登录用户回复的评论的创建者
                    .setReceiveId(addCommentVo.getToCommentUserId());
            map.put("toCommentId", addCommentVo.getToCommentId());
        } else {
            //通知为评论博文类型
            event.setTopic(NoticeConst.TOPIC_COMMENT_ARTICLE)
                    //评论为博文评论
                    //接收者就是写当前博文的作者
                    .setReceiveId(articleService.getById(addCommentVo.getTypeId()).getCreateBy());
        }
        map.put("articleId", addCommentVo.getTypeId());
        map.put("commentId", comment.getId());
        event.setData(map);
        //设置发送者id
        event.setSendId(SecurityUtils.getUserId());
        producer.fireEvent(event);
    }

    /**
     * 通过根评论的rootId查询对应的子评论
     *
     * @param rootId :
     * @param likeType
     * @return java.util.List<zh.nb.vo.CommentListVo> :
     * @author zh
     * @date 2022/2/23 15:39
     **/
    private List<CommentListDTO> getChildren(Long rootId, int likeType) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //根据评论的rootId
        queryWrapper.eq(Comment::getRootId, rootId);
        //按照时间顺序排序
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> commentList = list(queryWrapper);

        return toCommentDTOList(commentList, likeType);
    }

    /**
     * 封装为vo
     *
     * @param commentList :
     * @param type        点赞类型
     * @return java.util.List<zh.nb.vo.CommentListVo> :
     * @author zh
     * @date 2022/2/23 15:44
     **/
    private List<CommentListDTO> toCommentDTOList(List<Comment> commentList, Integer type) {
        final int likeType = type;
        //转换为vo
        List<CommentListDTO> commentLists = BeanCopyUtils.copyBeanList(commentList, CommentListDTO.class);
        //通过createBy属性查询到评论人的昵称
        commentLists = commentLists.stream()
                //查询这些评论的用户名
                .map(comment -> comment.setNickName(userService.getById(comment.getCreateBy()).getNickName())
                        .setAvatar(userService.getById(comment.getCreateBy()).getAvatar())
                        .setIsLike(likeService.isLike(comment.getId(), likeType)))
                //通过判断toCommentUserId是否为-1,不是-1就查询toCommentUserName
                //查询这些评论的回复的用户名
                .map(commentListVo -> !commentListVo.getToCommentUserId().equals(CommentConst.TO_COMMENT_USERID_DEFAULT)
                        ? commentListVo.setToCommentUserName(userService.getById(commentListVo.getToCommentUserId()).getNickName())
                        : commentListVo).collect(Collectors.toList());
        return commentLists;
    }

    @Override
    public int getCommentCount(Long userId) {
        //获得所有用户创作的文章id集合
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Article::getCreateBy, userId)
                .select(Article::getId);
        List<Object> articleIds = articleService.listObjs(queryWrapper);
        //如果没有文章,那么被评论的个数就是0
        if (Objects.isNull(articleIds) || articleIds.size() <= 0) {
            return 0;
        }
        LambdaQueryWrapper<Comment> query = new LambdaQueryWrapper<>();
        query.eq(Comment::getType, CommentConst.ARTICLE_COMMENT_TYPE).in(Comment::getTypeId, articleIds);
        //获得所有评论总数
        return count(query);
    }

    @Override
    public ResponseResult<List<NewCommentDTO>> getNewComment(Long aid) {
        //获得作者id
        Article article = articleService.lambdaQuery().eq(Article::getId, aid).select(Article::getCreateBy).one();
        Long authorId = article.getCreateBy();
        //获取所有的文章id
        LambdaQueryWrapper<Article> queryArticleIds = new LambdaQueryWrapper<>();
        queryArticleIds.eq(Article::getCreateBy, authorId).select(Article::getId);
        List<Object> articleIds = articleService.listObjs(queryArticleIds);
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //获取最新的5条评论
        queryWrapper
                .eq(Comment::getType, CommentConst.ARTICLE_COMMENT_TYPE).in(Comment::getTypeId, articleIds)
                .orderByDesc(Comment::getCreateTime)
                .select(Comment::getTypeId, Comment::getCreateBy, Comment::getContent)
                .last(" limit 5");
        List<Comment> newCommentList = list(queryWrapper);
        List<NewCommentDTO> newComments = BeanCopyUtils.copyBeanList(newCommentList, NewCommentDTO.class);
        LambdaQueryChainWrapper<Comment> commentLambdaQueryChainWrapper = lambdaQuery();
        //处理数据
        newComments = newComments.stream()
                .map(newComment -> newComment.setTitle(articleService.lambdaQuery().eq(Article::getId, newComment.getTypeId()).select(Article::getTitle).one().getTitle())
                        .setNickName(userService.lambdaQuery().eq(User::getId, newComment.getCreateBy()).select(User::getNickName).one().getNickName()))
                .collect(Collectors.toList());
        return ResponseResult.okResult(newComments);
    }
}

