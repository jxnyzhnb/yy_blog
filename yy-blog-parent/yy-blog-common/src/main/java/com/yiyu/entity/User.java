package com.yiyu.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * (User)表实体类
 *
 * @author makejava
 * @since 2022-06-03 16:50:12
 */
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("sys_user")
public class User {
    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 账号状态(0正常,1停用)
     */
    private Integer status;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 手机号
     */
    private String phonenumber;
    /**
     * 性别(0男,1女,2未知)
     */
    private String sex;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 创建人id
     */
    private Long createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人id
     */
    private Long updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 删除标志(0表示未删除,1表示已删除)
     */
    private Integer delFlag;
    /**
     * 用户类型(0管理员,1用户)
     */
    private Integer type;
    /**
     * 个性签名
     */
    private String signature;
    /**
     * 用户登录ip
     */
    private String ipAddr;
    /**
     * ip来源
     */
    private String ipSource;
}

