package com.dimples.core.transport;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dimples.core.eunm.CodeMsgEnum;
import com.dimples.core.page.metadata.Page;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
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

    @Setter
    @Getter
    private Integer code;

    @Setter
    @Getter
    private String msg;

    @Setter
    @Getter
    private List<T> data;

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
     * 成功带数据
     **/
    public static <T> Result success(Object object) {
        Result.ResultBuilder responseBuilder = Result.builder();
        if (ObjectUtil.isEmpty(object)) {
            return failed(CodeMsgEnum.DB_RESOURCE_NULL);
        }
        if (object instanceof IPage<?>) {
            IPage<?> page = (IPage<?>) object;
            if (ObjectUtil.isEmpty(page)) {
                return failed(CodeMsgEnum.DB_RESOURCE_NULL);
            }
            if (CollUtil.isEmpty(page.getRecords())) {
                return failed(CodeMsgEnum.DB_RESOURCE_NULL);
            }
            responseBuilder.code(CodeMsgEnum.SUCCESS.getCode());
            responseBuilder.msg(CodeMsgEnum.SUCCESS.getMessage());
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
                return failed(CodeMsgEnum.DB_RESOURCE_NULL);
            }
            if (CollUtil.isEmpty(page.getRecords())) {
                return failed(CodeMsgEnum.DB_RESOURCE_NULL);
            }
            responseBuilder.code(CodeMsgEnum.SUCCESS.getCode());
            responseBuilder.msg(CodeMsgEnum.SUCCESS.getMessage());
            responseBuilder.data(page.getRecords());
            responseBuilder.currentPage(page.getCurrent());
            responseBuilder.pageSize(page.getSize());
            responseBuilder.pagesTotal(page.getPages());
            responseBuilder.recordsTotal(page.getTotal());
            return responseBuilder.build();
        }

        if (object instanceof List) {
            if (CollUtil.isEmpty((List<?>) object)) {
                return failed(CodeMsgEnum.DB_RESOURCE_NULL);
            }
            responseBuilder.recordsTotal((long) ((List<?>) object).size());
        }
        responseBuilder.code(CodeMsgEnum.SUCCESS.getCode());
        responseBuilder.msg(CodeMsgEnum.SUCCESS.getMessage());
        responseBuilder.data(CollUtil.newArrayList(object));
        responseBuilder.currentPage(MIN_DATA_SIZE);
        responseBuilder.pageSize(MIN_DATA_SIZE);
        responseBuilder.pagesTotal(MIN_DATA_SIZE);
        responseBuilder.recordsTotal(MIN_DATA_SIZE);
        return responseBuilder.build();
    }

    public static Result failed(CodeMsgEnum resultCodeEnum) {
        Result.ResultBuilder responseBuilder = Result.builder();
        responseBuilder.code(resultCodeEnum.getCode());
        responseBuilder.msg(resultCodeEnum.getMessage());
        responseBuilder.data(CollUtil.newArrayList());
        responseBuilder.currentPage(NULL_DATA_SIZE);
        responseBuilder.pageSize(NULL_DATA_SIZE);
        responseBuilder.pagesTotal(NULL_DATA_SIZE);
        responseBuilder.recordsTotal(NULL_DATA_SIZE);
        return responseBuilder.build();
    }

}














