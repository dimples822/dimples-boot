package com.dimples.core.monitor.entity;

import com.dimples.core.util.Arith;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * CPU相关信息
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/7/30
 */
@Setter
public class Cpu {

    /**
     * 核心数
     */
    @Getter
    @ApiModelProperty(value = "核心数")
    private int cpuNum;

    /**
     * CPU总的使用率
     */
    @ApiModelProperty(value = "CPU总的使用率")
    private double total;

    /**
     * CPU系统使用率
     */
    @ApiModelProperty(value = "CPU系统使用率")
    private double sys;

    /**
     * CPU用户使用率
     */
    @ApiModelProperty(value = "CPU用户使用率")
    private double used;

    /**
     * CPU当前等待率
     */
    @ApiModelProperty(value = "CPU当前等待率")
    private double wait;

    /**
     * CPU当前空闲率
     */
    @ApiModelProperty(value = "CPU当前空闲率")
    private double free;

    public double getTotal() {
        return Arith.round(Arith.mul(total, 100), 2);
    }

    public double getSys() {
        return Arith.round(Arith.mul(sys / total, 100), 2);
    }


    public double getUsed() {
        return Arith.round(Arith.mul(used / total, 100), 2);
    }

    public double getWait() {
        return Arith.round(Arith.mul(wait / total, 100), 2);
    }


    public double getFree() {
        return Arith.round(Arith.mul(free / total, 100), 2);
    }

}
