package com.dimples.core.transport;

import com.alibaba.fastjson.JSONObject;
import com.dimples.core.eunm.CodeAndMessageEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseVO {

    public static final String NOT_FOUND_DATA = "未找到数据";
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
     * 成功但不带数据
     **/
    public static ResponseVO success() {
        ResponseVO result = new ResponseVO();
        result.setCode(CodeAndMessageEnum.SUCCESS.getCode());
        result.setMsg(CodeAndMessageEnum.SUCCESS.getMessage());
        return result;
    }

    /**
     * 成功带数据
     **/
    public static ResponseVO success(Object object) {
        ResponseVO result = new ResponseVO();
        result.setCode(CodeAndMessageEnum.SUCCESS.getCode());
        result.setMsg(CodeAndMessageEnum.SUCCESS.getMessage());
        result.setData(object);
        return result;
    }

    /**
     * 操作失败
     *
     * @return ResponseDTO
     */
    public static ResponseVO failed() {
        ResponseVO result = new ResponseVO();
        result.setCode(CodeAndMessageEnum.FAIL.getCode());
        result.setMsg(CodeAndMessageEnum.FAIL.getMessage());
        result.setData(Lists.newArrayList());
        return result;
    }

    /**
     * 失败
     **/
    public static ResponseVO failed(Integer code, String msg) {
        ResponseVO result = new ResponseVO();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(Lists.newArrayList());
        return result;
    }

    public static ResponseVO failed(String message) {
        ResponseVO result = new ResponseVO();
        result.setCode(CodeAndMessageEnum.FAIL.getCode());
        result.setMsg(message);
        result.setData(Lists.newArrayList());
        return result;
    }

    public static ResponseVO failed(CodeAndMessageEnum resultCodeEnum) {
        ResponseVO result = new ResponseVO();
        result.setCode(resultCodeEnum.getCode());
        result.setMsg(resultCodeEnum.getMessage());
        result.setData(Lists.newArrayList());
        return result;
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
    public static void makeResponse(HttpServletResponse response, String contentType,
                                    int status, Object value) throws IOException {
        response.setContentType(contentType);
        response.setStatus(status);
        response.getOutputStream().write(JSONObject.toJSONString(value).getBytes());
    }

    public static ResponseVO successWithOther(Object object, Map<String, Object> other) {
        ResponseVO result = new ResponseVO();
        result.setCode(CodeAndMessageEnum.SUCCESS.getCode());
        result.setMsg(CodeAndMessageEnum.SUCCESS.getMessage());
        result.setData(object);
        result.setOther(other);
        return result;
    }

    /**
     * 失败
     **/
    public static ResponseVO error(Integer code, String msg) {
        ResponseVO result = new ResponseVO();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Map<String, Object> buildResult(Object param1, Object param2) {
        Map<String, Object> r = Maps.newHashMap();
        r.put("total", param1);
        r.put("result", param2);
        return r;
    }
}














