package com.yiyu.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

/**
 * @author zh
 * @ClassName : com.yiyu.seckill.utils.MD5Util
 * @Description :
 * Created by user on 2022-03-09 19:26:27
 * Copyright  2020 user. All rights reserved.
 */
@Component
public class MD5Util {
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    /**
     * 加密salt
     **/
    private static final String salt = "1a2b3c4d";

    /**
     * 把客户端传回的密码进行第一次加密
     *
     * @param inputPass: 客户端传回密码
     * @return java.lang.String :
     * @author zh
     * @date 2022/3/9 19:32
     **/
    public static String inputPassToFromPass(String inputPass) {
        //再把salt进行分散,进行加密
        String str = "" + salt.charAt(0) + salt.charAt(4) + inputPass +
                salt.charAt(2) + salt.charAt(5);
        return md5(str);
    }

    /**
     * 对客户端密码进行二次加密
     *
     * @param fromPass: 客户端密码加密后的密码
     * @param salt:     存入数据库的salt
     * @return java.lang.String :
     * @author zh
     * @date 2022/3/9 19:36
     **/
    public static String fromPassToDBPass(String fromPass, String salt) {

        String str = ""+salt.charAt(1) + salt.charAt(4) + fromPass + salt.charAt(2) + salt.charAt(5);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass, String salt) {
        String fromPass = inputPassToFromPass(inputPass);
        return fromPassToDBPass(fromPass, salt);
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFromPass("123456"));
        System.out.println(fromPassToDBPass("5ca8afa3002cbbc67262d5c517e017f1", "1a2b3c4d"));
        System.out.println(inputPassToDBPass("123456", "1a2b3c4d"));
    }
}
