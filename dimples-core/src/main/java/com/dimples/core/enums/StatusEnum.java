package com.dimples.core.enums;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * 状态
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/12/29
 */
public enum StatusEnum {

    /**
     * W
     */
    ACTIVE("1", "启用"),
    /**
     * E
     */
    DISABLE("0", "停用"),
    /**
     * DI
     */
    DELETE("-1", "删除");

    public static Map<String, String> map = new HashMap<>();

    static {
        StatusEnum[] values = StatusEnum.values();
        if (values.length > 0) {
            for (StatusEnum categoryEnum : values) {
                map.put(categoryEnum.getStatusCode(), categoryEnum.getStatusDesc());
            }
        }
    }

    StatusEnum(String statusCode, String statusDesc) {
        this.statusCode = statusCode;
        this.statusDesc = statusDesc;
    }

    /**
     * Code
     */
    @Setter
    @Getter
    private String statusCode;

    /**
     * Desc
     */
    @Setter
    @Getter
    private String statusDesc;

}
