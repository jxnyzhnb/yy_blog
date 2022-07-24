package com.yiyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiyu.constants.ArticleConst;
import com.yiyu.constants.NoticeConst;
import com.yiyu.entity.Event;
import com.yiyu.entity.Follow;
import com.yiyu.entity.Notice;
import com.yiyu.event.EventProducer;
import com.yiyu.mapper.FollowMapper;
import com.yiyu.service.ArticleService;
import com.yiyu.service.FollowService;
import com.yiyu.service.NoticeService;
import com.yiyu.service.UserService;
import com.yiyu.utils.ResponseResult;
import com.yiyu.utils.SecurityUtils;
import com.yiyu.vo.front.FollowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Objects;

/**
 * (Follow)表服务实现类
 *
 * @author makejava
 * @since 2022-05-17 21:03:21
 */
@Service("followService")
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {
    @Autowired
    private UserService userService;
    @Autowired
    private EventProducer producer;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private ArticleService articleService;

    @Override
    public boolean getFollow(Long toUserId) {
        //获取登录用户
        Long userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper<>();
        //登录用户查询是否关注过该作者
        queryWrapper.eq(Follow::getUserId, userId)
                .eq(Follow::getToUserId, toUserId);
        Follow one = getOne(queryWrapper);
        return Objects.nonNull(one);
    }

    @Transactional
    @Override
    public ResponseResult<Object> follow(FollowVo followVo) {
        //获取登录用户
        Long userId = SecurityUtils.getUserId();
        //进行关注作者信息的插入
        Follow follow = new Follow();
        follow.setToUserId(followVo.getToUserId())
                .setUserId(userId)
                .setType(followVo.getType());
        save(follow);
        //通知的封装与消费
        Event eventFollow = new Event();
        HashMap<String, Object> followMap = new HashMap<>();
        //关注来源是文章详情以外的地方,是为homePage
        if (followVo.getType().equals(ArticleConst.FOLLOW_TYPE_OTHER)) {
            followMap.put("source", NoticeConst.FOLLOW_HOMEPAGE);
        } else if (followVo.getType().equals(ArticleConst.FOLLOW_TYPE_ARTICLE)) {
            //关注来源是文章详情
            followMap.put("source", NoticeConst.FOLLOW_ARTICLE_DETAIL);
            //如果是文章详情就是文章id
            followMap.put("sourceId", followVo.getTypeId());
        } else if (followVo.getType().equals(ArticleConst.FOLLOW_TYPE_DISCUSS)) {
            //关注来源是帖子详情
            followMap.put("source", NoticeConst.FOLLOW_ARTICLE_DETAIL);
            //如果是帖子详情就是帖子id
            followMap.put("sourceId", followVo.getTypeId());
        }
        eventFollow.setTopic(NoticeConst.TOPIC_FOLLOW)
                .setSendId(userId)
                .setReceiveId(followVo.getToUserId())
                .setEventType(NoticeConst.T_TYPE_FOLLOW)
                .setData(followMap);
        producer.fireEvent(eventFollow);
        //被关注的用户自动给关注用户发送私信,感谢关注
        Event eventLetter = new Event();
        HashMap<String, Object> letterMap = new HashMap<>();
        letterMap.put("msg", NoticeConst.FOLLOW_LETTER_DEFAULT);
        eventLetter.setTopic(NoticeConst.TOPIC_LETTER)
                .setSendId(followVo.getToUserId())
                .setReceiveId(userId)
                .setEventType(NoticeConst.T_TYPE_LETTER)
                .setData(letterMap);
        producer.fireEvent(eventLetter);
        return ResponseResult.okResult();
    }

    @Transactional
    @Override
    public ResponseResult<Object> unFollow(FollowVo followVo) {
        //获取登录用户
        Long userId = SecurityUtils.getUserId();
        //进行关注作者信息的删除
        LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Follow::getToUserId, followVo.getToUserId())
                .eq(Follow::getUserId, userId);
        remove(queryWrapper);
        //删除关注通知和删除关注后的私信通知
        LambdaQueryWrapper<Notice> query = new LambdaQueryWrapper<>();
        query.eq(Notice::getFromId, userId)
                .eq(Notice::getToId, followVo.getToUserId())
                .eq(Notice::getTType, NoticeConst.T_TYPE_FOLLOW)
                .or()
                .eq(Notice::getFromId, followVo.getToUserId())
                .eq(Notice::getToId, userId)
                .eq(Notice::getTType, NoticeConst.T_TYPE_LETTER);
        noticeService.remove(query);
        return ResponseResult.okResult();
    }

    @Override
    public int getFollowCount(Long userId) {
        LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Follow::getToUserId, userId);
        return count(queryWrapper);
    }
}

