package com.dimples.core.monitor.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统相关信息
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/7/30
 */
@Setter
@Getter
public class Sys {

    /**
     * 服务器名称
     */
    @ApiModelProperty(value = "服务器名称")
    private String computerName;

    /**
     * 服务器Ip
     */
    @ApiModelProperty(value = "服务器Ip")
    private String computerIp;

    /**
     * 项目路径
     */
    @ApiModelProperty(value = "项目路径")
    private String userDir;

    /**
     * 操作系统
     */
    @ApiModelProperty(value = "操作系统")
    private String osName;

    /**
     * 系统架构
     */
    @ApiModelProperty(value = "系统架构")
    private String osArch;


}
