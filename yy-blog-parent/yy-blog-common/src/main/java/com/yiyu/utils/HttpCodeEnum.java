package com.yiyu.utils;

public enum HttpCodeEnum {
    // 成功
    SUCCESS(200, "操作成功"),
    // 登录
    NEED_LOGIN(401, "需要登录后操作"),
    NOT_FOUND(404, "资源不存在"),
    NO_OPERATOR_AUTH(403, "无权限操作"),
    SYSTEM_ERROR(500, "出现错误"),
    USERNAME_EXIST(501, "用户名已存在"),
    PHONENUMBER_EXIST(502, "手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    NICKNAME_EXIST(514, "昵称已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    REQUIRE_PASSWORD(507, "必需填写密码"),
    REQUIRE_NICKNAME(512, "必需填写昵称"),
    REQUIRE_EMAIL(513, "必需填写邮箱"),
    LOGIN_ERROR(505, "用户名或密码错误"),
    REQUIRE_USER(506, "必须填写用户信息"),
    WRONG_FILE_FORMAT(509, "文件格式错误,请上传png或jpg文件"),
    FILE_IS_NULL(510, "文件为空"),
    FILE_UPLOAD_FILE(511, "文件上传失败"),
    COMMENT_NOT_NULL(508, "评论内容不能为空"),
    PARAMETER_ERROR(520, "参数不合法"),
    TAGS_EXIST(521, "标签已存在"),
    LINK_EXIST(522, "友链已存在"),
    METHOD_ARGUMENT_VALID_FAIL(533,"方法参数校验异常"),
    OBJECT_ARGUMENT_VALID_FAIL(534,"对象内部变量校验异常");
    int code;
    String msg;

    HttpCodeEnum(int code, String errorMessage) {
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
