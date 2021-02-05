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
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return DateUtil.formatDateTime(new Date(time));
    }

    /**
     * JDK运行时间
     */
    public String getRunTime() {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = System.currentTimeMillis() - DateUtil.parseDateTime(getStartTime()).getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

}
