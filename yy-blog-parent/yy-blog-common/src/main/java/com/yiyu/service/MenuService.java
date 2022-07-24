package com.yiyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yiyu.dto.back.UserMenuDTO;
import com.yiyu.entity.Menu;
import com.yiyu.utils.ResponseResult;

import java.util.List;

/**
 * (Menu)表服务接口
 *
 * @author makejava
 * @since 2022-06-01 20:59:21
 */
public interface MenuService extends IService<Menu> {
      ResponseResult<List<UserMenuDTO>>  getUserMenu();
}

