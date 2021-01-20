package com.dimples.core.transport;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dimples.core.eunm.CodeMsgEnum;
import com.dimples.core.page.metadata.Page;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 通用返回结果类
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/1
 */
@SuppressWarnings("unchecked")
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {

    public static final String NOT_FOUND_DATA = "未找到数据";
    public static final long MIN_DATA_SIZE = 1L;
    public static final long NULL_DATA_SIZE = 0L;

    @ApiModelProperty(value = "返回代码")
    @Setter
    @Getter
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    @Setter
    @Getter
    private String msg;

    @ApiModelProperty(value = "返回数据")
    @Setter
    @Getter
    private List<T> data;

    @ApiModelProperty(value = "返回数据")
    @Setter
    @Getter
    private Map<String, Object> other;

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    @Setter
    @Getter
    private Long currentPage;

    /**
     * 页大小
     */
    @ApiModelProperty(value = "页大小")
    @Setter
    @Getter
    private Long pageSize;

    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    @Setter
    @Getter
    private Long pagesTotal;

    /**
     * 总条数
     */
    @ApiModelProperty(value = "数据总量")
    @Setter
    @Getter
    private Long recordsTotal;

    /**
     * 成功但不带数据
     **/
    public static <T> Result<T> success() {
        Result.ResultBuilder<T> responseBuilder = Result.builder();
        return responseBuilder
                .code(CodeMsgEnum.SUCCESS.getCode())
                .msg(CodeMsgEnum.SUCCESS.getMessage())
                .build();
    }


    /**
     * 成功带数据
     **/
    public static <T> Result<T> success(Object object) {
        Result.ResultBuilder<T> responseBuilder = Result.builder();
        if (ObjectUtil.isEmpty(object)) {
            return failed(CodeMsgEnum.DB_RESOURCE_NULL);
        }
        if (object instanceof IPage<?>) {
            IPage<T> page = (IPage<T>) object;
            return buildResult(responseBuilder, page.getRecords(), page.getCurrent(), page.getSize(), page.getPages(), page.getTotal());
        }

        if (object instanceof Page<?>) {
            Page<T> page = (Page<T>) object;
            return buildResult(responseBuilder, page.getRecords(), page.getCurrent(), page.getSize(), page.getPages(), page.getTotal());
        }

        if (object instanceof List<?>) {
            if (CollUtil.isEmpty((List<?>) object)) {
                return failed(CodeMsgEnum.DB_RESOURCE_NULL);
            }
            responseBuilder.data((List<T>) object);
            responseBuilder.recordsTotal((long) ((List<T>) object).size());
        } else {
            responseBuilder.data(CollUtil.newArrayList((T) object));
        }
        responseBuilder.code(CodeMsgEnum.SUCCESS.getCode());
        responseBuilder.msg(CodeMsgEnum.SUCCESS.getMessage());
        responseBuilder.currentPage(MIN_DATA_SIZE);
        responseBuilder.pageSize(MIN_DATA_SIZE);
        responseBuilder.pagesTotal(MIN_DATA_SIZE);
        responseBuilder.recordsTotal(MIN_DATA_SIZE);
        return responseBuilder.build();
    }

    private static <T> Result<T> buildResult(ResultBuilder<T> responseBuilder, List<T> records, long current, long size, long pages, long total) {
        if (CollUtil.isEmpty(records)) {
            return failed(CodeMsgEnum.DB_RESOURCE_NULL);
        }
        responseBuilder.code(CodeMsgEnum.SUCCESS.getCode());
        responseBuilder.msg(CodeMsgEnum.SUCCESS.getMessage());
        responseBuilder.data(records);
        responseBuilder.currentPage(current);
        responseBuilder.pageSize(size);
        responseBuilder.pagesTotal(pages);
        responseBuilder.recordsTotal(total);
        return responseBuilder.build();
    }

    public static <T> Result<T> failed(CodeMsgEnum resultCodeEnum) {
        Result.ResultBuilder<T> responseBuilder = Result.builder();
        responseBuilder.code(resultCodeEnum.getCode());
        responseBuilder.msg(resultCodeEnum.getMessage());
        responseBuilder.data(CollUtil.newArrayList());
        responseBuilder.currentPage(NULL_DATA_SIZE);
        responseBuilder.pageSize(NULL_DATA_SIZE);
        responseBuilder.pagesTotal(NULL_DATA_SIZE);
        responseBuilder.recordsTotal(NULL_DATA_SIZE);
        return responseBuilder.build();
    }

    /**
     * 操作失败
     *
     * @return ResponseDTO
     */
    public static <T> Result<T> failed() {
        Result.ResultBuilder<T> responseBuilder = Result.builder();
        responseBuilder.code(CodeMsgEnum.FAIL.getCode());
        responseBuilder.msg(CodeMsgEnum.FAIL.getMessage());
        responseBuilder.data(CollUtil.newArrayList());
        responseBuilder.currentPage(NULL_DATA_SIZE);
        responseBuilder.pageSize(NULL_DATA_SIZE);
        responseBuilder.pagesTotal(NULL_DATA_SIZE);
        responseBuilder.recordsTotal(NULL_DATA_SIZE);
        return responseBuilder.build();
    }

    /**
     * 失败
     **/
    public static <T> Result<T> failed(Integer code, String msg) {
        Result.ResultBuilder<T> responseBuilder = Result.builder();
        responseBuilder.code(code);
        responseBuilder.msg(msg);
        responseBuilder.data(CollUtil.newArrayList());
        responseBuilder.currentPage(NULL_DATA_SIZE);
        responseBuilder.pageSize(NULL_DATA_SIZE);
        responseBuilder.pagesTotal(NULL_DATA_SIZE);
        responseBuilder.recordsTotal(NULL_DATA_SIZE);
        return responseBuilder.build();
    }

    public static <T> Result<T> failed(String message) {
        Result.ResultBuilder<T> responseBuilder = Result.builder();
        responseBuilder.code(CodeMsgEnum.FAIL.getCode());
        responseBuilder.msg(message);
        responseBuilder.data(CollUtil.newArrayList());
        responseBuilder.currentPage(NULL_DATA_SIZE);
        responseBuilder.pageSize(NULL_DATA_SIZE);
        responseBuilder.pagesTotal(NULL_DATA_SIZE);
        responseBuilder.recordsTotal(NULL_DATA_SIZE);
        return responseBuilder.build();
    }

    /**
     * 设置响应
     *
     * @param response    HttpServletResponse
     * @param contentType content-type
     * @param status      http状态码
     * @param value       响应内容
     * @throws IOException IOException
     */
    public static void buildResponse(HttpServletResponse response, String contentType,
                                     int status, Object value) throws IOException {
        response.setContentType(contentType);
        response.setStatus(status);
        response.setCharacterEncoding("UTF-8");
        response.getOutputStream().write(JSONUtil.toJsonStr(value).getBytes(StandardCharsets.UTF_8));
    }

    public static void buildResponse(HttpServletResponse response, String contentType,
                                     int status, byte[] value) throws IOException {
        response.setContentType(contentType + ";charset=UTF-8");
        response.setStatus(status);
        response.setCharacterEncoding("UTF-8");
        response.getOutputStream().write(value);
    }

    public static <T> Result<T> successWithOther(T object, Map<String, Object> other) {
        Result.ResultBuilder<T> responseBuilder = Result.builder();
        responseBuilder.code(CodeMsgEnum.SUCCESS.getCode());
        responseBuilder.msg(CodeMsgEnum.SUCCESS.getMessage());
        responseBuilder.data(CollUtil.newArrayList(object));
        responseBuilder.other(other);
        responseBuilder.currentPage(MIN_DATA_SIZE);
        responseBuilder.pageSize(MIN_DATA_SIZE);
        responseBuilder.pagesTotal(MIN_DATA_SIZE);
        responseBuilder.recordsTotal(MIN_DATA_SIZE);
        return responseBuilder.build();
    }

    /**
     * 错误
     **/
    public static <T> Result<T> error(Integer code, String msg) {
        Result.ResultBuilder<T> responseBuilder = Result.builder();
        responseBuilder.code(code);
        responseBuilder.msg(msg);
        responseBuilder.currentPage(NULL_DATA_SIZE);
        responseBuilder.pageSize(NULL_DATA_SIZE);
        responseBuilder.pagesTotal(NULL_DATA_SIZE);
        responseBuilder.recordsTotal(NULL_DATA_SIZE);
        return responseBuilder.build();
    }

}














