package com.dimples.core.transport;

import lombok.Data;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/1
 */
@Data
public class RequestWithPageDTO {

    private String page;

    private String pageSize;

    private String params;

}
