package com.yiyu.exception;

import com.yiyu.utils.HttpCodeEnum;

/**
 * @author zh
 * @ClassName : zh.nb.exception.SystemException
 * @Description :统一异常
 * Created by user on 2022-02-22 19:53:20
 * Copyright  2020 user. All rights reserved.
 */

public class SystemException extends RuntimeException{
    private int code;
    private String msg;

    public SystemException(HttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
