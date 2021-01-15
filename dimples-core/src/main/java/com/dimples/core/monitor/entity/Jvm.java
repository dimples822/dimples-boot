package com.dimples.core.monitor.entity;

import com.dimples.core.util.Arith;

import java.lang.management.ManagementFactory;
import java.util.Date;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Setter;

/**
 * JVM相关信息
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/7/30
 */
@Setter
public class Jvm {

    /**
     * 当前JVM占用的内存总数(M)
     */
    @ApiModelProperty(value = "当前JVM占用的内存总数(M)")
    private double total;

    /**
     * JVM最大可用内存总数(M)
     */
    @ApiModelProperty(value = "JVM最大可用内存总数(M)")
    private double max;

    /**
     * JVM空闲内存(M)
     */
    @ApiModelProperty(value = "JVM空闲内存(M)")
    private double free;

    /**
     * JDK版本
     */
    @ApiModelProperty(value = "JDK版本")
    private String version;

    /**
     * JDK路径
     */
    @ApiModelProperty(value = "JDK路径")
    private String home;

    public double getTotal() {
        return Arith.div(total, (1024 * 1024), 2);
    }

    public double getMax() {
        return Arith.div(max, (1024 * 1024), 2);
    }

    public double getFree() {
        return Arith.div(free, (1024 * 1024), 2);
    }

    public double getUsed() {
        return Arith.div(total - free, (1024 * 1024), 2);
    }

    public double getUsage() {
        return Arith.mul(Arith.div(total - free, total, 4), 100);
    }

    /**
     * 获取JDK名称
     */
    public String getName() {
        return ManagementFactory.getRuntimeMXBean().getVmName();
    }

    public String getVersion() {
        return version;
    }

    public String getHome() {
        return home;
    }

    /**
     * JDK启动时间
     */
    public String getStartTime() {
        return DateUtil.formatDateTime(com.dimples.core.util.DateUtil.getServerStartDate());
    }

    /**
     * JDK运行时间
     */
    public String getRunTime() {
        return com.dimples.core.util.DateUtil.getDatePoor(new Date(), com.dimples.core.util.DateUtil.getServerStartDate());
    }

}
