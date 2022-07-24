package com.yiyu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiyu.constants.NoticeConst;
import com.yiyu.dto.front.LetterDTO;
import com.yiyu.dto.front.LetterUserDTO;
import com.yiyu.dto.front.NoticeDTO;
import com.yiyu.dto.front.PageDTO;
import com.yiyu.entity.Like;
import com.yiyu.entity.Notice;
import com.yiyu.entity.User;
import com.yiyu.mapper.NoticeMapper;
import com.yiyu.service.*;
import com.yiyu.utils.BeanCopyUtils;
import com.yiyu.utils.ResponseResult;
import com.yiyu.utils.SecurityUtils;
import com.yiyu.vo.front.NoticeConditionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (Notice)表服务实现类
 *
 * @author makejava
 * @since 2022-05-01 16:40:26
 */
@Service("noticeService")
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private LikeService likeService;
    @Autowired
    private DiscussService discussService;

    @Transactional
    @Override
    public void addMessage(Notice notice) {
        //插入通知
        save(notice);
        //如果通知类型是点赞就将通知id和点赞记录进行关联,方便之后删除取消点赞是删除点赞通知
        if (notice.getTType().equals(NoticeConst.T_TYPE_LIKE)) {
            HashMap<String, Object> map = JSONObject.parseObject(notice.getContent(), HashMap.class);
            String likeId = (String) map.get("likeId");
            LambdaUpdateWrapper<Like> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Like::getId, likeId)
                    .set(Like::getNoticeId, notice.getId());
            likeService.update(updateWrapper);
        }
    }

    @Override
    public ResponseResult<PageDTO<NoticeDTO>> getNewNotice(NoticeConditionVo conditionVo) {
        LambdaQueryWrapper<Notice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Notice::getToId, SecurityUtils.getUserId());
        queryWrapper.eq(Notice::getTType, conditionVo.getTType());
        queryWrapper.orderByDesc(Notice::getCreateTime);
        Page<Notice> page = new Page<>(conditionVo.getPageNum(), conditionVo.getPageSize());
        page(page, queryWrapper);
        List<Notice> noticeList = page.getRecords();
        List<NoticeDTO> notices = BeanCopyUtils.copyBeanList(noticeList, NoticeDTO.class);
        if (NoticeConst.T_TYPE_COMMENT.equals(conditionVo.getTType())) {
            getCommentNotice(notices);
        } else if (NoticeConst.T_TYPE_LIKE.equals(conditionVo.getTType())) {
            getLikeNotice(notices);
        } else if (NoticeConst.T_TYPE_NOTICE.equals(conditionVo.getTType())) {
            notices = getSystemNotice(notices);
        }
        return ResponseResult.okResult(new PageDTO<>(notices, page.getTotal()));
    }

    private List<NoticeDTO> getSystemNotice(List<NoticeDTO> noticeVos) {
        return null;

    }

    private List<NoticeDTO> getLikeNotice(List<NoticeDTO> noticeVos) {
        for (NoticeDTO noticeVo : noticeVos) {
            Long fromId = noticeVo.getFromId();
            User fromUser = userService.getById(fromId);
            noticeVo.setAvatar(fromUser.getAvatar())
                    .setNickName(fromUser.getNickName());
            Notice notice = getById(noticeVo.getId());
            String content = notice.getContent();
            HashMap<String, Object> map = JSONObject.parseObject(content, HashMap.class);
            HashMap<String, Object> dataMap = new HashMap<>();

            if (NoticeConst.TOPIC_LIKE_ARTICLE.equals(noticeVo.getType()) || NoticeConst.TOPIC_LIKE_ARTICLE_COMMENT.equals(noticeVo.getType())) {
                if (NoticeConst.TOPIC_LIKE_ARTICLE_COMMENT.equals(noticeVo.getType())) {
                    dataMap.put("comment", commentService.getById((Serializable) map.get("commentId")).getContent());
                }
                String articleId = (String) map.get("articleId");
                dataMap.put("articleId", map.get("articleId"));
                dataMap.put("title", articleService.getById(articleId).getTitle());
            } else if (NoticeConst.TOPIC_LIKE_DISCUSS.equals(noticeVo.getType()) || NoticeConst.TOPIC_LIKE_DISCUSS_COMMENT.equals(noticeVo.getType())) {
                if (NoticeConst.TOPIC_LIKE_DISCUSS_COMMENT.equals(noticeVo.getType())) {
                    dataMap.put("comment", commentService.getById((Serializable) map.get("commentId")).getContent());
                }
                String discussId = (String) map.get("discussId");
                dataMap.put("discussId", map.get("discussId"));
                dataMap.put("title", discussService.getById(discussId).getTitle());
            }
            noticeVo.setData(dataMap);
        }
        return noticeVos;
    }

    private List<NoticeDTO> getCommentNotice(List<NoticeDTO> noticeVos) {

        for (NoticeDTO noticeVo : noticeVos) {
            Long fromId = noticeVo.getFromId();
            User fromUser = userService.getById(fromId);
            noticeVo.setAvatar(fromUser.getAvatar())
                    .setNickName(fromUser.getNickName());
            Notice notice = getById(noticeVo.getId());
            String content = notice.getContent();
            HashMap<String, Object> map = JSONObject.parseObject(content, HashMap.class);
            HashMap<String, Object> dataMap = new HashMap<>();
            //如果是回复评论
            if (NoticeConst.TOPIC_REPLY_ARTICLE_COMMENT.equals(noticeVo.getType())||NoticeConst.TOPIC_COMMENT_ARTICLE.equals(noticeVo.getType())) {
                //设置回复评论的评论内容
                if (NoticeConst.TOPIC_REPLY_ARTICLE_COMMENT.equals(noticeVo.getType())){
                    dataMap.put("toComment", commentService.getById((Serializable) map.get("toCommentId")).getContent());
                }
                //设置评论内容
                dataMap.put("comment", commentService.getById((Serializable) map.get("commentId")).getContent());
                String articleId = (String) map.get("articleId");
                //设置博客标题
                dataMap.put("title", articleService.getById(articleId).getTitle());
                //设置博客id
                dataMap.put("articleId", articleId);

            }else if (NoticeConst.TOPIC_REPLY_DICUSS_COMMENT.equals(noticeVo.getType())||NoticeConst.TOPIC_COMMENT_DISCUSS.equals(noticeVo.getType())){
                if (NoticeConst.TOPIC_REPLY_DICUSS_COMMENT.equals(noticeVo.getType())){
                    dataMap.put("toComment", commentService.getById((Serializable) map.get("toCommentId")).getContent());
                }
                //设置评论内容
                dataMap.put("comment", commentService.getById((Serializable) map.get("commentId")).getContent());
                String discussId = (String) map.get("discussId");
                //设置博客标题
                dataMap.put("title", discussService.getById(discussId).getTitle());

                //设置博客id
                dataMap.put("discussId", discussId);
            }
            noticeVo.setData(dataMap);
        }
        return noticeVos;
    }

    @Override
    public ResponseResult<Boolean> getIsRead() {
        LambdaQueryWrapper<Notice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Notice::getIsRead, NoticeConst.IS_READ_DEFAULT);
        queryWrapper.eq(Notice::getToId, SecurityUtils.getUserId());
        boolean unread = count(queryWrapper) > 0;
        return ResponseResult.okResult(unread);
    }

    @Override
    public ResponseResult<Map<String, String>> getUnread() {
        Long toId = SecurityUtils.getUserId();
        Integer unreadNotice = noticeMapper.countByIsRead(toId, NoticeConst.IS_READ_DEFAULT, NoticeConst.T_TYPE_NOTICE);
        Integer unreadComment = noticeMapper.countByIsRead(toId, NoticeConst.IS_READ_DEFAULT, NoticeConst.T_TYPE_COMMENT);
        Integer unreadLike = noticeMapper.countByIsRead(toId, NoticeConst.IS_READ_DEFAULT, NoticeConst.T_TYPE_LIKE);
        Integer unreadLetter = noticeMapper.countByIsRead(toId, NoticeConst.IS_READ_DEFAULT, NoticeConst.T_TYPE_LETTER);
        Map<String, String> map = new HashMap<>();
        map.put("notice", unreadNotice.toString());
        map.put("comment", unreadComment.toString());
        map.put("like", unreadLike.toString());
        map.put("letter", unreadLetter.toString());
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult<Object> setRead(String tType) {
        Long userId = SecurityUtils.getUserId();
        LambdaUpdateWrapper<Notice> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Notice::getToId, userId);
        updateWrapper.eq(Notice::getTType, tType);
        updateWrapper.eq(Notice::getIsRead, NoticeConst.IS_READ_DEFAULT);
        updateWrapper.set(Notice::getIsRead, NoticeConst.IS_READ_NORMAL);
        update(updateWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<Object> delete(Long noticeId) {
        removeById(noticeId);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<List<LetterDTO>> getLetterList(Long toId) {
        Long userId = SecurityUtils.getUserId();
        List<Notice> letterList = noticeMapper.getLetterList(userId, toId, NoticeConst.TYPE_LETTER);
        List<LetterDTO> letterVos = BeanCopyUtils.copyBeanList(letterList, LetterDTO.class);
        letterVos = letterVos.stream()
                .map(letterVo ->
                        letterVo.setAvatar(userService.getById(letterVo.getFromId()).getAvatar())
                                .setContent((String) JSONObject.parseObject(letterVo.getContent(), HashMap.class).get("msg")))
                .map(letterVo -> letterVo.getFromId().equals(userId) ? letterVo.setSelf(true) : letterVo.setSelf(false))
                .collect(Collectors.toList());
        return ResponseResult.okResult(letterVos);
    }

    @Override
    public ResponseResult<List<LetterUserDTO>> getUserList() {
        Long userId = SecurityUtils.getUserId();
        //1.查询所有接收者为当前登录用户的私信信息的用户id
        LambdaQueryWrapper<Notice> fromWrapper = new LambdaQueryWrapper<>();
        fromWrapper
                .eq(Notice::getToId, userId)
                .eq(Notice::getTType, NoticeConst.TYPE_LETTER)
                .select(Notice::getFromId);
        List<Object> fromIds = listObjs(fromWrapper);
        //1.查询所有发送者为当前登录用户的私信信息的用户id
        LambdaQueryWrapper<Notice> toWrapper = new LambdaQueryWrapper<>();
        toWrapper
                .eq(Notice::getFromId, userId)
                .eq(Notice::getTType, NoticeConst.TYPE_LETTER)
                .select(Notice::getToId);
        List<Object> toIds = listObjs(toWrapper);
        //合并两个用户id
        fromIds.addAll(toIds);
        //进行查询设置数据
        List<LetterUserDTO> letterUsers = fromIds.stream()
                .distinct()
                .map(id -> new LetterUserDTO().setUserId((Long) id))
                .map(letterUserVo -> letterUserVo.setNickName(userService.getById(letterUserVo.getUserId()).getNickName()))
                .map(letterUserVo -> letterUserVo.setAvatar(userService.getById(letterUserVo.getUserId()).getAvatar()))
                .map(letterUserVo -> letterUserVo.setContent((String) JSONObject.parseObject(noticeMapper.getNewestLetter(letterUserVo.getUserId(), userId, NoticeConst.T_TYPE_LETTER).getContent(), HashMap.class).get("msg")))
                .map(letterUserVo -> letterUserVo.setCreateTime(noticeMapper.getNewestLetter(letterUserVo.getUserId(), userId, NoticeConst.T_TYPE_LETTER).getCreateTime()))
                .map(letterUserVo -> letterUserVo.setIsRead(noticeMapper.getNewestLetter(letterUserVo.getUserId(), userId, NoticeConst.T_TYPE_LETTER).getIsRead() == 0))
                .collect(Collectors.toList());
        return ResponseResult.okResult(letterUsers);
    }

    @Transactional
    @Override
    public ResponseResult<Object> setLetterRead(Long toId) {
        Long userId = SecurityUtils.getUserId();
        LambdaUpdateWrapper<Notice> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(Notice::getToId, userId)
                .eq(Notice::getFromId, toId)
                .set(Notice::getIsRead, NoticeConst.IS_READ_NORMAL);
        update(updateWrapper);
        Integer unRead = noticeMapper.countByIsRead(userId, NoticeConst.IS_READ_DEFAULT, NoticeConst.T_TYPE_LETTER);
        return ResponseResult.okResult(unRead);
    }
}

