package com.dimples.biz.system.controller;

import com.dimples.core.annotation.OpsLog;
import com.dimples.core.eunm.OpsLogTypeEnum;
import com.dimples.core.transport.ResponseDTO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户登陆
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/14
 */
@Slf4j
@Api(value = "用户登录模块", tags = "用户登录模块")
@RestController
public class LoginController {

    @ApiOperation(value = "用户登陆", notes = "用户登陆")
    @OpsLog(value = "用户登陆", type = OpsLogTypeEnum.LOGIN)
    @PostMapping("/login")
    public ResponseDTO login(@ApiParam(name = "username", value = "用户名", required = true) String username,
                             @ApiParam(name = "password", value = "密码", required = true) String password,
                             @RequestParam(defaultValue = "false") Boolean remember) {

        return ResponseDTO.success();
    }

}















