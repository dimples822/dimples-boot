package com.dimples.controller.monitor;

import com.dimples.core.annotation.OpsLog;
import com.dimples.core.eunm.OpsLogTypeEnum;
import com.dimples.core.monitor.ServerInfo;
import com.dimples.core.transport.ResponseVO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/7/30
 */
@Slf4j
@Api(tags = "监控信息模块")
@RestController
@RequestMapping("monitor")
public class ServerInfoController {

    @ApiOperation(value = "获取服务器信息")
    @OpsLog(value = "获取服务器信息", type = OpsLogTypeEnum.SELECT)
    @GetMapping("system")
    public ResponseVO server() {
        ServerInfo server = new ServerInfo();
        server.copyTo();
        return ResponseVO.success(server);
    }

}
