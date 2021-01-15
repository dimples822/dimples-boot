package com.dimples.controller.monitor;

import com.dimples.core.annotation.OpsLog;
import com.dimples.core.eunm.OpsLogTypeEnum;
import com.dimples.core.job.ServerMonitorInfoJob;
import com.dimples.core.monitor.ServerInfo;
import com.dimples.core.transport.Result;
import com.github.benmanes.caffeine.cache.Cache;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import cn.hutool.core.util.ObjectUtil;
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

    @Resource
    private Cache<String, Object> caffeineCache;

    @ApiOperation(value = "获取服务器信息")
    @OpsLog(value = "获取服务器信息", type = OpsLogTypeEnum.SELECT)
    @GetMapping("system")
    public Result<ServerInfo> server() {
        ServerInfo serverInfo = (ServerInfo) caffeineCache.asMap().get(ServerMonitorInfoJob.SERVER_MONITOR_CACHE_KEY);
        if (ObjectUtil.isEmpty(serverInfo)) {
            serverInfo = new ServerInfo();
            serverInfo.copyTo();
        }
        return Result.success(serverInfo);
    }

}
