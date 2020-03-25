package com.dimples.controller;

import com.dimples.core.annotation.OpsLog;
import com.dimples.core.eunm.OpsLogTypeEnum;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.util.StrUtil;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/2/28
 */
@RestController
public class TestController {

    @OpsLog(value = "test1", type = OpsLogTypeEnum.TEST)
    @GetMapping("test1")
    public void test1() {
        System.out.println("test-1");
    }

}
