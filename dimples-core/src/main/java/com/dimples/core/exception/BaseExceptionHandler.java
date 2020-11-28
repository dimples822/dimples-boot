package com.dimples.core.exception;

import com.alibaba.fastjson.JSON;
import com.dimples.core.constant.DimplesConstant;
import com.dimples.core.eunm.CodeMsgEnum;
import com.dimples.core.transport.ResponseVO;
import com.dimples.core.util.HttpContextUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;

import lombok.extern.slf4j.Slf4j;

/**
 * 基础异常捕捉
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/12/27
 */
@Slf4j
public class BaseExceptionHandler {

    /**
     * BizException
     *
     * @param e BizException
     * @return ResponseDTO
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseVO handleBizException(BizException e) {
        log.error("业务错误", e);
        this.buildErrorInfo();
        return ResponseVO.failed(e.getMessage());
    }

    @ExceptionHandler(value = DataException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseVO handleDataException(DataException e) {
        log.error(DimplesConstant.LOG_STR, "数据异常");
        this.buildErrorInfo();
        log.error("错误信息: 【 {} 】", e.getMessage());
        return ResponseVO.failed(e.getMessage());
    }

    /**
     * AccessDeniedException
     *
     * @return ResponseDTO
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseVO handleAccessDeniedException() {
        this.buildErrorInfo();
        return ResponseVO.failed(CodeMsgEnum.NOT_AUTH);
    }

    /**
     * 接口请求方式错误
     *
     * @param e HttpRequestMethodNotSupportedException
     * @return ResponseDTO
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseVO excelDataConvertException(HttpRequestMethodNotSupportedException e) {
        log.error("接口请求方式错误: " + e);
        this.buildErrorInfo();
        return ResponseVO.error(CodeMsgEnum.METHOD_NOT_ALLOWED.getCode(), e.getMethod());
    }

    /**
     * 统一处理请求参数校验(普通传参)
     *
     * @param e ConstraintViolationException
     * @return ResponseDTO
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseVO handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            Path path = violation.getPropertyPath();
            String[] pathArr = StringUtils.splitByWholeSeparatorPreserveAllTokens(path.toString(), ".");
            message.append(" 【").append(pathArr[1]).append("】 ").append(violation.getMessage()).append(", ");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        this.buildErrorInfo();
        return ResponseVO.failed(CodeMsgEnum.REQUEST_PARAM_NULL.getCode(), message.toString());
    }

    /**
     * 统一处理请求参数校验(实体对象传参)
     *
     * @param e BindException
     * @return ResponseDTO
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseVO handleBindException(BindException e) {
        StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            message.append(" 【").append(error.getField()).append("】 ").append(error.getDefaultMessage()).append(", ");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        this.buildErrorInfo();
        return ResponseVO.failed(message.toString());
    }

    /**
     * Exception
     *
     * @param e Exception
     * @return ResponseDTO
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseVO handleException(Exception e) {
        log.error(DimplesConstant.LOG_STR, "系统内部异常");
        buildErrorInfo();
        log.error("异常信息: ", e);
        return ResponseVO.failed(CodeMsgEnum.SERVER_ERROR);
    }


    private void buildErrorInfo() {
        //获取所有参数的map集合
        HttpServletRequest request = HttpContextUtil.getRequest();
        Map<String, String[]> parameterMap = request.getParameterMap();
        String param = JSON.toJSONString(parameterMap);
        String uri = request.getRequestURI();
        log.error("\n=================================== {} ===============================\n" +
                        "【 参数: {}】\n" +
                        "【 URI: {}】\n" +
                        "=========================================================================="
                , "抛出异常的请求参数", param, uri);
    }
}















