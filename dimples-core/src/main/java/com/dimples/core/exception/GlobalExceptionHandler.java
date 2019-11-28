package com.dimples.core.exception;

import com.dimples.core.eunm.CodeAndMessageEnum;
import com.dimples.core.result.ResultCommon;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常捕获<br>
 *
 * @author zhongyj
 * @date 2019/7/1
 */
@Slf4j
@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 拦截业务异常
     *
     * @param e BizException
     * @return ResultCommon
     */
    @ExceptionHandler(BizException.class)
    public ResultCommon bizException(BizException e) {
        log.error("业务异常:" + e.getMessage());
        return ResultCommon.error(e.getCode(), e.getMessage());
    }

    /**
     * 拦截系统异常
     *
     * @param e Exception
     * @return ResultCommon
     */
    @ExceptionHandler(RuntimeException.class)
    public ResultCommon exceptionHandler(RuntimeException e) {
        log.error("系统异常:" + e.getMessage());
        return ResultCommon.error(CodeAndMessageEnum.SERVER_ERROR.getCode(), CodeAndMessageEnum.SERVER_ERROR.getMessage());
    }

}













