package com.dimples.core.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * 通用的枚举
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/7
 */
public enum CodeMsgEnum {

    /**
     * 文件上传
     */
    FILE_READING_ERROR(400, "文件读取错误"),
    // 文件不存在
    FILE_NOT_FOUND(400, "文件不存在"),
    // 上传图片出错
    UPLOAD_ERROR(500, "上传图片出错"),
    // 上传文件出错
    UPLOAD_FILE_ERROR(500, "上传文件出错"),

    /**
     * 权限问题
     */
    // 权限异常
    NO_PERMISSION(403, "权限异常"),
    // 不能删除超级管理员
    CANT_DELETE_ADMIN(600, "不能删除超级管理员"),
    // 不能冻结超级管理员
    CANT_FREEZE_ADMIN(600, "不能冻结超级管理员"),
    // 不能修改超级管理员角色
    CANT_CHANGE_ADMIN(600, "不能修改超级管理员角色"),
    /**
     * 登录信息失效
     */
    LOGIN_OUT_TIME(302, "登录信息已失效"),

    /**
     * 身份验证失败（签名错误）
     */
    UNAUTHORIZED(401, "身份验证失败（签名错误）"),
    /**
     * token无效
     */
    AUTH_DISABLE(402, "Token无效,请获取正确的Token"),

    /**
     * 没有权限
     */
    NOT_AUTH(403, "没有权限访问该资源"),
    /**
     * 账户问题
     */
    // 该用户已经注册
    USER_ALREADY_REG(401, "该用户已经注册"),
    // 没有此用户
    USER_NOT_EXISTED(400, "没有此用户"),
    // 账号被冻结
    ACCOUNT_FREEZE(401, "账号被冻结"),
    // 原密码不正确
    OLD_PWD_NOT_RIGHT(402, "原密码不正确"),
    // 两次输入密码不一致
    TWO_PWD_NOT_MATCH(405, "两次输入密码不一致"),
    //新密码为空
    NEW_PWD_EMPTY(405, "新密码不能为空"),

    /**
     * 数据问题
     */
    // 数据库中没有该资源
    DB_RESOURCE_NULL(400, "数据库中没有该资源"),
    // 请求数据格式不正确
    REQUEST_INVALIDATE(400, "请求数据格式不正确"),
    // 验证码不正确
    INVALID_KAPTCHA(400, "验证码不正确"),
    // 找不到资源
    NOT_FOUND(404, "找不到资源"),


    /**
     * 错误的请求
     */
    // 请求参数为空
    REQUEST_PARAM_NULL(400, "请求参数为空"),
    // 会话超时
    SESSION_TIMEOUT(400, "会话超时"),
    /**
     * 请求中指定的方法不被允许
     */
    METHOD_NOT_ALLOWED(405, "请求中指定的方法不被允许"),

    /**
     * 请求头中指定的一些前提条件失败，例如请求参数错误或为空
     */
    PRECONDITION_FAILED(412, "请求头错误"),


    // 服务器异常
    SERVER_ERROR(500, "服务器异常,请联系管理员"),
    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 操作失败
     */
    FAIL(300, "操作失败"),
    /**
     * 操作失败
     */
    FIELD_INCOMPLETE(301, "必需字段数据不完整");

    CodeMsgEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Setter
    @Getter
    private int code;
    @Setter
    @Getter
    private String message;

}
