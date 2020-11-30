package com.dimples.core.transport;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dimples.core.eunm.CodeAndMessageEnum;
import com.dimples.core.page.Page;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 通用返回结果类
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/1
 */
@ToString
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class R {

    public static final String NOT_FOUND_DATA = "未找到数据";
    public static final long MIN_DATA_SIZE = 1L;
    public static final long NULL_DATA_SIZE = 0L;

    @Setter
    @Getter
    private Integer code;

    @Setter
    @Getter
    private String msg;

    @Setter
    @Getter
    private Object data;

    @Setter
    @Getter
    private Map<String, Object> other;

    /**
     * 当前页
     */
    @Setter
    @Getter
    private Long currentPage;

    /**
     * 页大小
     */
    @Setter
    @Getter
    private Long pageSize;

    /**
     * 总页数
     */
    @Setter
    @Getter
    private Long pagesTotal;

    /**
     * 总条数
     */
    @Setter
    @Getter
    private Long recordsTotal;


    /**
     * 成功但不带数据
     **/
    public static R success() {
        return R.builder()
                .code(CodeAndMessageEnum.SUCCESS.getCode())
                .msg(CodeAndMessageEnum.SUCCESS.getMessage())
                .build();
    }

    /**
     * 成功带数据
     **/
    public static R success(Object object) {
        RBuilder responseBuilder = R.builder();
        if (ObjectUtil.isEmpty(object)) {
            return failed(CodeAndMessageEnum.DB_RESOURCE_NULL);
        }
        if (object instanceof IPage<?>) {
            IPage<?> page = (IPage<?>) object;
            if (ObjectUtil.isEmpty(page)) {
                return failed(CodeAndMessageEnum.DB_RESOURCE_NULL);
            }
            if (CollUtil.isEmpty(page.getRecords())) {
                return failed(CodeAndMessageEnum.DB_RESOURCE_NULL);
            }
            responseBuilder.code(CodeAndMessageEnum.SUCCESS.getCode());
            responseBuilder.msg(CodeAndMessageEnum.SUCCESS.getMessage());
            responseBuilder.data(page.getRecords());
            responseBuilder.currentPage(page.getCurrent());
            responseBuilder.pageSize(page.getSize());
            responseBuilder.pagesTotal(page.getPages());
            responseBuilder.recordsTotal(page.getTotal());
            return responseBuilder.build();
        }

        if (object instanceof Page<?>) {
            Page<?> page = (Page<?>) object;
            if (ObjectUtil.isEmpty(page)) {
                return failed(CodeAndMessageEnum.DB_RESOURCE_NULL);
            }
            if (CollUtil.isEmpty(page.getRecords())) {
                return failed(CodeAndMessageEnum.DB_RESOURCE_NULL);
            }
            responseBuilder.code(CodeAndMessageEnum.SUCCESS.getCode());
            responseBuilder.msg(CodeAndMessageEnum.SUCCESS.getMessage());
            responseBuilder.data(page.getRecords());
            responseBuilder.currentPage(page.getCurrent());
            responseBuilder.pageSize(page.getSize());
            responseBuilder.pagesTotal(page.getPages());
            responseBuilder.recordsTotal(page.getTotal());
            return responseBuilder.build();
        }

        if (object instanceof List) {
            if (CollUtil.isEmpty((List<?>) object)) {
                return failed(CodeAndMessageEnum.DB_RESOURCE_NULL);
            }
            responseBuilder.recordsTotal((long) ((List<?>) object).size());
        }
        responseBuilder.code(CodeAndMessageEnum.SUCCESS.getCode());
        responseBuilder.msg(CodeAndMessageEnum.SUCCESS.getMessage());
        responseBuilder.data(object);
        responseBuilder.currentPage(MIN_DATA_SIZE);
        responseBuilder.pageSize(MIN_DATA_SIZE);
        responseBuilder.pagesTotal(MIN_DATA_SIZE);
        responseBuilder.recordsTotal(MIN_DATA_SIZE);
        return responseBuilder.build();
    }

    /**
     * 操作失败
     *
     * @return ResponseDTO
     */
    public static R failed() {
        RBuilder responseBuilder = R.builder();
        responseBuilder.code(CodeAndMessageEnum.FAIL.getCode());
        responseBuilder.msg(CodeAndMessageEnum.FAIL.getMessage());
        responseBuilder.data(Lists.newArrayList());
        responseBuilder.currentPage(NULL_DATA_SIZE);
        responseBuilder.pageSize(NULL_DATA_SIZE);
        responseBuilder.pagesTotal(NULL_DATA_SIZE);
        responseBuilder.recordsTotal(NULL_DATA_SIZE);
        return responseBuilder.build();
    }

    /**
     * 失败
     **/
    public static R failed(Integer code, String msg) {
        RBuilder responseBuilder = R.builder();
        responseBuilder.code(code);
        responseBuilder.msg(msg);
        responseBuilder.data(Lists.newArrayList());
        responseBuilder.currentPage(NULL_DATA_SIZE);
        responseBuilder.pageSize(NULL_DATA_SIZE);
        responseBuilder.pagesTotal(NULL_DATA_SIZE);
        responseBuilder.recordsTotal(NULL_DATA_SIZE);
        return responseBuilder.build();
    }

    public static R failed(String message) {
        RBuilder responseBuilder = R.builder();
        responseBuilder.code(CodeAndMessageEnum.FAIL.getCode());
        responseBuilder.msg(message);
        responseBuilder.data(Lists.newArrayList());
        responseBuilder.currentPage(NULL_DATA_SIZE);
        responseBuilder.pageSize(NULL_DATA_SIZE);
        responseBuilder.pagesTotal(NULL_DATA_SIZE);
        responseBuilder.recordsTotal(NULL_DATA_SIZE);
        return responseBuilder.build();
    }

    public static R failed(CodeAndMessageEnum resultCodeEnum) {
        RBuilder responseBuilder = R.builder();
        responseBuilder.code(resultCodeEnum.getCode());
        responseBuilder.msg(resultCodeEnum.getMessage());
        responseBuilder.data(Lists.newArrayList());
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

    public static R successWithOther(Object object, Map<String, Object> other) {
        RBuilder responseBuilder = R.builder();
        responseBuilder.code(CodeAndMessageEnum.SUCCESS.getCode());
        responseBuilder.msg(CodeAndMessageEnum.SUCCESS.getMessage());
        responseBuilder.data(object);
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
    public static R error(Integer code, String msg) {
        RBuilder responseBuilder = R.builder();
        responseBuilder.code(code);
        responseBuilder.msg(msg);
        responseBuilder.currentPage(NULL_DATA_SIZE);
        responseBuilder.pageSize(NULL_DATA_SIZE);
        responseBuilder.pagesTotal(NULL_DATA_SIZE);
        responseBuilder.recordsTotal(NULL_DATA_SIZE);
        return responseBuilder.build();
    }
}














