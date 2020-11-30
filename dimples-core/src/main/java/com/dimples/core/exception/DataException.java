package com.dimples.core.exception;

import com.dimples.core.eunm.CodeMsgEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义异常
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/7
 */
@Getter
@Setter
public class DataException extends RuntimeException {

    /**
     * 异常信息
     */
    private String message;

    /**
     * 异常状态码
     */
    private int code;

    public DataException(String msg) {
        super(msg);
        this.message = msg;
    }

    public DataException(String msg, int code) {
        super(msg);
        this.message = msg;
        this.code = code;
    }

    public DataException(CodeMsgEnum bizExceptionEnum) {
        super(bizExceptionEnum.getMessage());
        this.code = bizExceptionEnum.getCode();
        this.message = bizExceptionEnum.getMessage();
    }

}













