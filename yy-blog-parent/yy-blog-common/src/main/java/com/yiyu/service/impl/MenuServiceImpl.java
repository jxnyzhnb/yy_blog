package com.yiyu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiyu.constants.AdminConst;
import com.yiyu.dto.back.UserMenuDTO;
import com.yiyu.mapper.MenuMapper;
import com.yiyu.entity.Menu;
import com.yiyu.service.MenuService;
import com.yiyu.utils.BeanCopyUtils;
import com.yiyu.utils.ResponseResult;
import com.yiyu.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * (Menu)表服务实现类
 *
 * @author makejava
 * @since 2022-06-01 20:59:21
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public ResponseResult<List<UserMenuDTO>> getUserMenu() {
        //获得管理员id
        Long userId = SecurityUtils.getUserId();
        //获得菜单
        List<Menu> menuList = menuMapper.selectMeunByUserId(userId);
        System.out.println(menuList);
        //获得根菜单并按顺序排序
        List<Menu> rootMenus = menuList.stream()
                .filter(menu -> menu.getRootId().equals(AdminConst.MENU_ROOT_ID_DEFAULT))
                .sorted(Comparator.comparing(Menu::getOrderNum))
                .collect(Collectors.toList());
        //转换成DTO
        List<UserMenuDTO> rootMenuList = BeanCopyUtils.copyBeanList(rootMenus, UserMenuDTO.class);
        //获得二级菜单
        Map<Long,List<Menu>> secondMeuns=menuList.stream()
                .filter(menu -> !menu.getRootId().equals(AdminConst.MENU_ROOT_ID_DEFAULT))
                .sorted(Comparator.comparing(Menu::getOrderNum))
                .collect(Collectors.groupingBy(Menu::getRootId));


        //合并菜单
        rootMenuList=rootMenuList.stream()

                //设置子菜单
                //为有子菜单的跟菜单设置子菜单，为没有子菜单的跟菜单设置自己为子菜单
                .map(rootMenu-> Objects.nonNull(secondMeuns.get(rootMenu.getId()))?rootMenu.setChildren(BeanCopyUtils.copyBeanList(secondMeuns.get(rootMenu.getId()),UserMenuDTO.class)):
                        rootMenu.setChildren(Collections.singletonList(new UserMenuDTO().setComponent(rootMenu.getComponent()).setIcon(rootMenu.getIcon()).setName(rootMenu.getName()).setPath(""))).setComponent(AdminConst.MENU_ROOT_COMPONMENT).setName(null))
                .collect(Collectors.toList());
        return ResponseResult.okResult(rootMenuList);
    }
}

