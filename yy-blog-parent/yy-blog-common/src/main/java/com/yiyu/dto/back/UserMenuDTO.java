package com.yiyu.dto.back;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMenuDTO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 路径
     */
    private String path;

    /**
     * 组件
     */
    private String component;

    /**
     * icon
     */
    private String icon;

    /**
     * 是否禁用
     */
    private int status;

    /**
     * 子菜单列表
     */
    private List<UserMenuDTO> children;
    /**
     * 是否隐藏(0显示，1隐藏)
     */
    private Integer isHidden;

}
