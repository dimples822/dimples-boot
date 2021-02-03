package com.dimples.core.redis;

import com.dimples.core.helper.RedisHelper;
import com.dimples.core.helper.StringRedisHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2021/2/3
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Resource
    private StringRedisHelper stringRedisHelper;
    @Resource
    private RedisHelper redisHelper;

    @Test
    public void addStringRedis() {
        stringRedisHelper.set("dimples-string-test", "欢迎使用!!!");
    }

    @Test
    public void addRedis() {
        redisHelper.set("dimples-test", "欢迎使用!!!");
    }

}
