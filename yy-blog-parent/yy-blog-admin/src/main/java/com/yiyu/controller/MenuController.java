package com.yiyu.controller;
import com.yiyu.annotation.SystemLog;
import com.yiyu.dto.back.UserMenuDTO;
import com.yiyu.service.MenuService;
import com.yiyu.utils.ResponseResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * (Menu)表控制层
 *
 * @author makejava
 * @since 2022-06-01 20:59:16
 */
@RestController
@RequestMapping("/menu")
public class MenuController{
    /**
     * 服务对象
     */
    @Autowired
    private MenuService menuService;
    @SystemLog(businessName = "获取管理员对应的菜单")
    @GetMapping("/userMenu")
    public ResponseResult<List<UserMenuDTO>> userMenu(){
        return menuService.getUserMenu();
    }

}

