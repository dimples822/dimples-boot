package com.dimples.boot.core.result;

/**
 * 响应状态码枚举类
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/1
 */
public enum ResultCodeEnum {

    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 操作失败
     */
    FAIL(300,"操作失败"),
    /**
     * 身份验证失败（签名错误）
     */
    UNAUTHORIZED(401,"身份验证失败（签名错误）"),
    /**
     * 找不到资源
     */
    NOT_FOUND(404,"找不到资源"),
    /**
     * 请求中指定的方法不被允许
     */
    METHOD_NOT_ALLOWED(405,"请求中指定的方法不被允许"),
    /**
     * 请求头中指定的一些前提条件失败，例如请求参数错误或为空
     */
    PRECONDITION_FAILED(412,"请求头错误"),
    /**
     * 服务器内部错误
     */
    SERVER_ERROR(500,"服务器内部错误");

    private Integer code;

    private String msg;

    ResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
