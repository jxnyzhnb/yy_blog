package com.yiyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiyu.constants.CommentConst;
import com.yiyu.constants.DiscussConst;
import com.yiyu.dto.front.DiscussDetailDTO;
import com.yiyu.dto.front.DiscussListDTO;
import com.yiyu.dto.front.PageDTO;
import com.yiyu.entity.Article;
import com.yiyu.entity.Comment;
import com.yiyu.entity.Discuss;
import com.yiyu.entity.User;
import com.yiyu.mapper.DiscussMapper;
import com.yiyu.service.CommentService;
import com.yiyu.service.DiscussService;
import com.yiyu.service.UserService;
import com.yiyu.utils.BeanCopyUtils;
import com.yiyu.utils.DateUtils;
import com.yiyu.utils.RedisCache;
import com.yiyu.utils.ResponseResult;
import com.yiyu.vo.front.DiscussConditonVo;
import com.yiyu.vo.front.DiscussVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * (Discuss)表服务实现类
 *
 * @author makejava
 * @since 2022-06-17 14:09:15
 */
@Service("discussService")
public class DiscussServiceImpl extends ServiceImpl<DiscussMapper, Discuss> implements DiscussService {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult<PageDTO<DiscussListDTO>> discussList(DiscussConditonVo discussConditonVo) {
        Page<Discuss> page = new Page<>(discussConditonVo.getPageNum(), discussConditonVo.getPageSize());
        LambdaQueryWrapper<Discuss> queryWrapper = new LambdaQueryWrapper<>();
        //需要按帖子最新发布进行排序
        if (discussConditonVo.getType().equals(DiscussConst.DISCUSS_TYPE_NEW_PULISH)) {
            queryWrapper.orderByDesc(Discuss::getCreateTime);
            //需要按帖子最新回复进行排序
        } else if (discussConditonVo.getType().equals(DiscussConst.DISCUSS_TYPE_NEW_REPLY)) {
            LambdaQueryWrapper<Comment> query = new LambdaQueryWrapper<>();
            query.eq(Comment::getType, CommentConst.DISCUSS_COMMENT_TYPE)
                    .orderByDesc(Comment::getCreateTime)
                    .select(Comment::getTypeId);
            //按照评论时间顺序获取帖子的id
            List<Object> discussIds = commentService.listObjs(query);
            if (discussIds.size() > 0) {
                queryWrapper.in(Discuss::getId, discussIds);
            }
        }
        //分页
        page(page, queryWrapper);
        List<Discuss> discussList = page.getRecords();
        //封装数据
        List<DiscussListDTO> discussLists = discussList.stream()
                .map(discuss -> {
                    Long createBy = discuss.getCreateBy();
                    User one = userService.lambdaQuery().eq(User::getId, createBy)
                            .select(User::getId, User::getNickName, User::getAvatar).one();
                    DiscussListDTO discussDTO = BeanCopyUtils.copyBean(discuss, DiscussListDTO.class);
                    discussDTO.setUserId(one.getId())
                            .setNickName(one.getNickName())
                            .setAvatar(one.getAvatar())
                            .setViewCount(redisCache.getCacheMap("discuss:viewCount",discussDTO.getId().toString()))
                            //设置帖子最新回复到现在的时间间隔
                            //如果查询帖子类型是最新发布就直接为”“
                            //如果查询帖子类型是最新回复,如果该帖子的评论不等于0就计算时间间隔;反之就直接为”“
                            .setRecentDisplayTime(
                                    discussConditonVo.getType().equals(DiscussConst.DISCUSS_TYPE_NEW_REPLY) ?
                                            (commentService.count(new LambdaQueryWrapper<Comment>().eq(Comment::getType, CommentConst.DISCUSS_COMMENT_TYPE).eq(Comment::getTypeId, discuss.getId())) != 0 ?
                                                    DateUtils.getRecentDisplayTime(
                                                            commentService.lambdaQuery()
                                                                    .eq(Comment::getType, CommentConst.DISCUSS_COMMENT_TYPE)
                                                                    .eq(Comment::getTypeId, discussDTO.getId()).orderByDesc(Comment::getCreateTime).last("limit 1").select(Comment::getCreateTime).one().getCreateTime()) : "") : "")
                            .setCommentCount(commentService.lambdaQuery().eq(Comment::getType, CommentConst.DISCUSS_COMMENT_TYPE).eq(Comment::getTypeId, discuss.getId()).count());
                    return discussDTO;
                }).collect(Collectors.toList());
        PageDTO<DiscussListDTO> pageDTO = new PageDTO<>(discussLists, page.getTotal());
        return ResponseResult.okResult(pageDTO);
    }

    @Override
    public ResponseResult<Object> addDiscuss(DiscussVo discussVo) {
        Discuss discuss = BeanCopyUtils.copyBean(discussVo, Discuss.class);
        save(discuss);
        return ResponseResult.okResult(discuss.getId());
    }

    @Override
    public ResponseResult<DiscussDetailDTO> getDiscussDetail(Long did) {
        DiscussDetailDTO discussDetail;

        Discuss discuss = lambdaQuery().eq(Discuss::getId, did)
                .select(Discuss::getId, Discuss::getTitle, Discuss::getContent, Discuss::getLikeCount, Discuss::getViewCount, Discuss::getCreateTime, Discuss::getCreateBy)
                .one();
        Long createBy = discuss.getCreateBy();
        User user = userService.lambdaQuery().eq(User::getId, createBy)
                .select(User::getId, User::getNickName, User::getAvatar).one();
        discussDetail = BeanCopyUtils.copyBean(discuss, DiscussDetailDTO.class);
        discussDetail.setCommentCount(commentService.lambdaQuery().eq(Comment::getType, CommentConst.DISCUSS_COMMENT_TYPE).eq(Comment::getTypeId, discuss.getId()).count())
                .setUserId(user.getId())
                .setAvatar(user.getAvatar())
                .setNickName(user.getNickName())
                .setViewCount(redisCache.getCacheMap("discuss:viewCount",did.toString()));
        return ResponseResult.okResult(discussDetail);
    }

    @Override
    public ResponseResult<Object> incrViewCount(Long discussId) {
        //访问数加1
        redisCache.incrMapKey("discuss:viewCount", discussId.toString(), 1L);
        return ResponseResult.okResult();
    }
    @Override
    public void viewCount(Long discussId, Integer viewCount) {
        LambdaUpdateWrapper<Discuss> updateWrapper = new LambdaUpdateWrapper<>();
        //根据id查询文章信息
        Discuss discuss = getById(discussId);
        //访问量加1
        discuss.setViewCount(viewCount);
        //更新信息
        updateWrapper.eq(Discuss::getId, discussId);
        updateWrapper.set(Discuss::getViewCount, viewCount);
        update(updateWrapper);
    }
}

