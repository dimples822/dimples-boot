package com.dimples.core.job;

import com.dimples.core.monitor.ServerInfo;
import com.github.benmanes.caffeine.cache.Cache;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/8/5
 */
@Slf4j
@Component
public class ServerMonitorInfoJob {

    public static final String SERVER_MONITOR_CACHE_KEY = "ServerInfo";

    @Resource
    private Cache<String, Object> caffeineCache;


    /**
     * 每隔5秒获取一次系统信息
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void cacheServerInfo() {
        ServerInfo server = new ServerInfo();
        server.copyTo();
        caffeineCache.put(SERVER_MONITOR_CACHE_KEY, server);
    }

}









