package com.yiyu.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zh
 * @ClassName : zh.nb.utils.BeanCopyUtils
 * @Description :bean赋值转换
 * Created by user on 2022-02-14 20:49:59
 * Copyright  2020 user. All rights reserved.
 */
public class BeanCopyUtils {
    private BeanCopyUtils() {
    }

    public static <T> T copyBean(Object source, Class<T> clazz) {
        T result = null;
        try {
            result = clazz.newInstance();
            BeanUtils.copyProperties(source, result);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <O,T> List<T> copyBeanList(List<O> source, Class<T> clazz) {
        //stream流知识,将集合转换为集合,map()方法是对集合中的元素进行一对一的转换
        //o代表的就是source集合的每一个元素,每个元素都有着对应的数据即copyBean(o, clazz)的返回值结果
        //将这些进行搜集变成一个list集合
        return source.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}
