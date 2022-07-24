package com.yiyu.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author yiyu
 * @version 1.0 2022-06-17 14:13:59
 * @ClassName : com.yiyu.dto.front.DiscussListDTO
 * @Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DiscussListDTO {
    /**
     * 帖子id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 发表时间与现在时间的间隔
     */
    private String recentDisplayTime;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 浏览量
     */
    private Integer viewCount;
    /**
     * 评论数
     */
    private Integer commentCount;
    /**
     * 点赞量
     */
    private Integer likeCount;
}
