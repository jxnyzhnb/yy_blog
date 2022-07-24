package com.yiyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiyu.constants.UserConst;
import com.yiyu.dto.front.UserBasicDTO;
import com.yiyu.dto.front.UserInfoDTO;
import com.yiyu.entity.User;
import com.yiyu.exception.SystemException;
import com.yiyu.mapper.UserMapper;
import com.yiyu.service.*;
import com.yiyu.utils.*;
import com.yiyu.vo.front.RegisterVo;
import com.yiyu.vo.front.UpdateUserInfoVo;
import com.yiyu.vo.front.UserBasicVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Objects;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-02-14 16:00:38
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private FollowService followService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private DiscussService discussService;

    @Override
    public ResponseResult<UserInfoDTO> userInfo()  {
        //从SecurityContextHolder拿到登录用户的id
        Long id = SecurityUtils.getUserId();
        //查询用户信息
        User user = getById(id);
        UserInfoDTO userInfos = BeanCopyUtils.copyBean(user, UserInfoDTO.class);
        return ResponseResult.okResult(userInfos);
    }

    @Override
    public ResponseResult<Object> updateUserInfo(UpdateUserInfoVo userInfoVo)  {
        User user = BeanCopyUtils.copyBean(userInfoVo, User.class);
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<Object> register(RegisterVo registerVo)  {
        //先检验参数
        if (!StringUtils.hasText(registerVo.getUserName())) {
            throw new SystemException(HttpCodeEnum.REQUIRE_USERNAME);
        }
        if (!StringUtils.hasText(registerVo.getNickName())) {
            throw new SystemException(HttpCodeEnum.REQUIRE_NICKNAME);
        }
        if (!StringUtils.hasText(registerVo.getEmail())) {
            throw new SystemException(HttpCodeEnum.REQUIRE_EMAIL);
        }
        if (!StringUtils.hasText(registerVo.getPassword())) {
            throw new SystemException(HttpCodeEnum.REQUIRE_PASSWORD);
        }
        //查询数据库中是否存在这些邮箱和用户名和昵称
        if (existUserName(registerVo.getUserName())) {
            throw new SystemException(HttpCodeEnum.USERNAME_EXIST);
        }
        if (existEmail(registerVo.getEmail())) {
            throw new SystemException(HttpCodeEnum.EMAIL_EXIST);
        }
        if (existNickName(registerVo.getNickName())) {
            throw new SystemException(HttpCodeEnum.NICKNAME_EXIST);
        }
        //对密码进行加密
        registerVo.setPassword(passwordEncoder.encode(registerVo.getPassword()));
        User user = BeanCopyUtils.copyBean(registerVo, User.class);
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<UserBasicDTO> profile(UserBasicVo userBasicVo) throws ParseException {

        return ResponseResult.okResult(getProfile(userBasicVo));
    }

    public UserBasicDTO getProfile(UserBasicVo userBasicVo) {
        //获取用户是否登录
        Boolean login = SecurityUtils.isLogin();
        //获取个人信息来源
        String source = userBasicVo.getSource();
        UserBasicDTO userBasicDTO = new UserBasicDTO();
        Long id;

        //通过博客详情获得个人信息
        if (source.equals(UserConst.SOURCE_ARTICLE_DETAIL)) {
            //获取作者id
            id = articleService.getById(userBasicVo.getId()).getCreateBy();

        } else if (source.equals(UserConst.SOURCE_HOME_PAGE)) {
            //通过个人主页来源
            //获取主页用户id
            Long userId = userBasicVo.getUserId();
            if (Objects.nonNull(userId)) {
                id = userId;
            } else {
                //说明是在浏览自己的主页
                id = SecurityUtils.getUserId();
            }
        } else if (source.equals(UserConst.SOURCE_DISCUSS_DETAIL)){
            id = discussService.getById(userBasicVo.getId()).getCreateBy();
        }else {
            throw new SystemException(HttpCodeEnum.PARAMETER_ERROR);
        }
        //获取用户信息
        User user = getById(id);
        userBasicDTO.setAvatar(user.getAvatar())
                .setId(id)
                .setNickName(user.getNickName())
                .setCodeAge(DateUtils.getIntervalDays(user.getCreateTime()))
                .setCommentCount(commentService.getCommentCount(id))
                .setFollowCount(followService.getFollowCount(id))
                .setLikeCount(likeService.getLikeCount(id))
                .setViewCount(articleService.getViewCount(id))
                .setArticleCount(articleService.getArticleCount(id))
                .setSelf(login && SecurityUtils.getUserId().equals(id))
                .setFollow(login && followService.getFollow(id));
        return userBasicDTO;
    }

    private boolean existUserName(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        User user = getOne(queryWrapper);
        return user != null;
    }

    private boolean existNickName(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName, nickName);
        User user = getOne(queryWrapper);
        return user != null;
    }

    private boolean existEmail(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        User user = getOne(queryWrapper);
        return user != null;
    }
}

