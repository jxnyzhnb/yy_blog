package com.yiyu.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.HashMap;

/**
 * @author zh
 * @ClassName : zh.nb.vo.NoticeVo
 * @Description :
 * Created by user on 2022-05-01 19:25:43
 * Copyright  2020 user. All rights reserved.
 */
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoticeDTO {
    /**
     * 通知id
     **/
    private Long id;
    /**
     * 评论用户头像
     **/
   private String avatar;
    /**
     * 评论用户名
     **/
   private String nickName;
    /**
     * 通知发起者
     */
   private Long fromId;
   /**
    * 评论内容
    **/
   private String comment;
   /**
    * 评论的博文标题
    **/
   private String title;
    /**
     * 评论的类型(comment_article代表博文评论,reply_comment代表评论回复)
     **/
   private String type;
    /**
     * 私信内容
     **/
   private String content;
    /**
     * 通知时间
     **/
    private Date createTime;
    /**
     * 信息
     **/
    private HashMap<String,Object> data;
}
