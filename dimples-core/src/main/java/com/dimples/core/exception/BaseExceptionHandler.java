package com.dimples.core.exception;

import com.dimples.core.enums.CodeMsgEnum;
import com.dimples.core.transport.Result;
import com.dimples.core.util.DUtil;
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

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 基础异常捕捉
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/12/27
 */
@Slf4j
public class BaseExceptionHandler {

    @ExceptionHandler(value = BizException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> handleBizException(BizException e) {
        this.buildErrorInfo("业务异常", e);
        return Result.failed(e.getMessage());
    }

    @ExceptionHandler(value = DataException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<String> handleDataException(DataException e) {
        this.buildErrorInfo("自定义数据异常", e);
        return Result.failed(e.getMessage());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result<String> handleAccessDeniedException(AccessDeniedException e) {
        this.buildErrorInfo("权限异常", e);
        return Result.failed(CodeMsgEnum.NOT_AUTH);
    }

    /**
     * 接口请求方式错误
     *
     * @param e HttpRequestMethodNotSupportedException
     * @return ResponseDTO
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result<String> excelDataConvertException(HttpRequestMethodNotSupportedException e) {
        this.buildErrorInfo("接口请求方式错误", e);
        return Result.error(CodeMsgEnum.METHOD_NOT_ALLOWED.getCode(), e.getMessage());
    }

    /**
     * 统一处理请求参数校验(普通传参)
     *
     * @param e ConstraintViolationException
     * @return ResponseDTO
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            Path path = violation.getPropertyPath();
            String[] pathArr = StringUtils.splitByWholeSeparatorPreserveAllTokens(path.toString(), ".");
            message.append(" 【").append(pathArr[1]).append("】 ").append(violation.getMessage()).append(", ");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        this.buildErrorInfo("参数验证不通过", e);
        return Result.failed(CodeMsgEnum.REQUEST_PARAM_NULL.getCode(), message.toString());
    }

    /**
     * 统一处理请求参数校验(实体对象传参)
     *
     * @param e BindException
     * @return ResponseDTO
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> handleBindException(BindException e) {
        StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            message.append(" 【").append(error.getField()).append("】 ").append(error.getDefaultMessage()).append(", ");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        this.buildErrorInfo("参数验证不通过", e);
        return Result.failed(message.toString());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> handleException(Exception e) {
        buildErrorInfo("系统内部异常", e);
        return Result.failed(CodeMsgEnum.SERVER_ERROR);
    }

    private void buildErrorInfo(String msg, Exception e) {
        //获取所有参数的map集合
        HttpServletRequest request = HttpContextUtil.getRequest();
        Map<String, String[]> parameterMap = request.getParameterMap();
        String param = JSONUtil.toJsonStr(parameterMap);
        String uri = request.getRequestURI();
        log.error("\n=================================== {} ===============================\n" +
                        "【 参   数: {}】\n" +
                        "【 U  R  I: {}】\n" +
                        "【 请 求 IP: {}】\n" +
                        "【 请求时间: {}】\n" +
                        "【 异常详情: {}】\n" +
                        "=========================================================================="
                , msg, param, uri, HttpContextUtil.getIp(), DateUtil.now(), ExceptionUtil.getRootCauseMessage(e));
    }
}















