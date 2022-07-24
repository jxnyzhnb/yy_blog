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
 * (Follow)表实体类
 *
 * @author makejava
 * @since 2022-05-17 21:03:21
 */
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("yy_follow")
public class Follow {
    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 进行关注用户id
     */
    private Long userId;
    /**
     * 被关注用户id
     */
    private Long toUserId;
    /**
     * 关注来源
     */
    private String type;
    /**
     * 关注来源id
     */
    private String typeId;
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

