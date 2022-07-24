package com.yiyu.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author zh
 * @ClassName : zh.nb.utils.PathUtils
 * @Description :
 * Created by user on 2022-03-03 12:16:24
 * Copyright  2020 user. All rights reserved.
 */
public class PathUtils {
    public static String generateFilePath(String fileName){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd/");
        String date = simpleDateFormat.format(new Date());
        int index = fileName.lastIndexOf(".");
        String fileType = fileName.substring(index);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return date + uuid + fileType;
    }

    public static void main(String[] args) {
        System.out.println(generateFilePath("1.jpg"));
    }
}
