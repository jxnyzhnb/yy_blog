package com.yiyu.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * (Like)表实体类
 *
 * @author makejava
 * @since 2022-05-06 10:49:49
 */
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("yy_like")
public class Like {
    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 点赞类型
     */
    private Integer type;
    /**
     * 文章id|评论id
     */
    private Long typeId;
    /**
     * 通知id
     */
    private Long noticeId;
    /**
     * 创建人id
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    /**
     * 修改人id
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private Integer delFlag;
}

