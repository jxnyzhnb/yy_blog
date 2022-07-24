package com.yiyu.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Menu)表实体类
 *
 * @author makejava
 * @since 2022-06-01 15:42:35
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("sys_menu")
public class Menu {
    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 菜单名
     */
    private String name;
    /**
     * 路由地址
     */
    private String path;
    /**
     * 组件路径
     */
    private String component;
    /**
     * 菜单状态(0显示,1隐藏)
     */
    private String visible;
    /**
     * 菜单状态(0正常,1停用)
     */
    private String status;
    /**
     * 权限标识
     */
    private String perms;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 创建人id
     */
    private Long createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改人id
     */
    private Long updateBy;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 是否删除(0未删除,1已删除)
     */
    private Integer delFlag;
    /**
     * 备注
     */
    private String remark;
    /**
     * 根id
     */
    private Long rootId;
    /**
     * 排序序号
     */
    private Integer orderNum;
    /**
     * 是否隐藏(0显示，1隐藏)
     */
    private Integer isHidden;
}

