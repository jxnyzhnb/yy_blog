package com.yiyu.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Message)表实体类
 *
 * @author makejava
 * @since 2022-06-03 10:15:03
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@TableName("yy_message")
public class Message {
    /**
     * 主键id
     */
    @TableId
    private Integer id;
    /**
     * 用户ip
     */
    private String ipAddress;
    /**
     * 用户地址
     */
    private String ipSource;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 留言内容
     */
    private String messageContent;
    /**
     * 弹幕速度
     */
    private Integer time;
    /**
     * 发布时间
     */
    private Date createTime;
}

