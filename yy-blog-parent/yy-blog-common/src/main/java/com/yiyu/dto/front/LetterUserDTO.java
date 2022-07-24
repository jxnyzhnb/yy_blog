package com.yiyu.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author zh
 * @ClassName : zh.nb.vo.LetterUserVo
 * @Description :
 * Created by user on 2022-05-11 21:30:26
 * Copyright  2020 user. All rights reserved.
 */
@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LetterUserDTO {
    private Long userId;
    private String nickName;
    private String avatar;
    private Date createTime;
    private String content;
    private Boolean isRead;
}
