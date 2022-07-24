package com.yiyu.dto.back;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yiyu
 * @version 1.0 2022-06-01 15:58:41
 * @ClassName : com.yiyu.dto.dmin.AdminInfoDTO
 * @Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminInfoDTO {
    /**
     * 主键
     */
    private Long id;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 个性签名
     */
    private String signature;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 邮箱
     */
    private String email;
}
