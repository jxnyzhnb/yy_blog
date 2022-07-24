package com.yiyu.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * (Notice)表实体类
 *
 * @author makejava
 * @since 2022-05-01 16:39:42
 */
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("yy_notice")
public class Notice {
    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 总类型(分为系统，评论，点赞，私信)
     */
    private String tType;
    /**
     * 通知类型（system_notice代表系统通知，comment_Article代表博文评论,
     * reply_comment代表回复评论，like_comment代表赞评论,like_article表示赞博文）
     */
    private String type;
    /**
     * 通知发起者（-1代表系统通知）
     */
    private Long fromId;
    /**
     * 通知接收者
     */
    private Long toId;
    /**
     * 通知内容
     **/
    private String content;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否已读(0表示未读，1表示已读)
     */
    private int isRead;
    /**
     *删除标志（0代表未删除，1代表已删除）
     **/
    private int delFlag;

}

