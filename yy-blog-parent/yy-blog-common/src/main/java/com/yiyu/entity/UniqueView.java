package com.yiyu.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (UniqueView)表实体类
 *
 * @author makejava
 * @since 2022-06-03 20:56:19
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("yy_unique_view")
public class UniqueView {
    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 独立访客数量
     */
    private Integer viewCount;
    /**
     * 时间段
     */
    private Date createTime;
}

