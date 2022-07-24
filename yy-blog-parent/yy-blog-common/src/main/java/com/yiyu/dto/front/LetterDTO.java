package com.yiyu.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

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
public class LetterDTO {
    /**
     * 评论用户头像
     **/
    private String avatar;
    /**
     * 通知发起者
     */
    private Long fromId;

    /**
     * 私信内容
     **/
    private String content;
    /**
     * 通知时间
     **/
    private Date createTime;
    /**
     * 通知时间
     **/
    private Boolean self;
}
