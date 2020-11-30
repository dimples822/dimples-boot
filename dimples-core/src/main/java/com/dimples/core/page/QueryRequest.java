package com.dimples.core.page;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/1
 */
@ToString
public class QueryRequest {

    public static final Integer DEFAULT_PAGE = 1;

    public static final Integer DEFAULT_PAGE_SIZE = 500;

    @Setter
    private Integer pageNumber;

    @Setter
    private Integer pageSize;


    /**
     * 排序字段
     */
    @Setter
    @Getter
    private String field;


    /**
     * 排序规则，asc升序，desc降序
     */
    @Setter
    @Getter
    private String order;

    public Integer getPageNumber() {
        if (ObjectUtil.isEmpty(pageNumber) || pageNumber < 1) {
            pageNumber = DEFAULT_PAGE;
        }
        return pageNumber;
    }

    public Integer getPageSize() {
        if (ObjectUtil.isEmpty(pageSize) || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    public QueryRequest() {
    }

    public QueryRequest(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }
}
