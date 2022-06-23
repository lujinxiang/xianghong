package com.xianghong.life.redis;

import com.xianghong.life.config.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;

/**
 * @author jinxianglu
 * @description redis工具类
 */
@Component
public class RedisUtils {

    private volatile static JedisPool jedisPool;

    @Autowired
    private RedisConfig redisConfig;

    @PostConstruct
    public void init() {
        jedisPool = redisConfig.jedisPool();
    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    public static void releaseJedisConnector(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
