package com.xianghong.life.config;

import com.xianghong.life.redis.JedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author jinxianglu
 * @EnableConfigurationProperties(JedisProperties.class): 开启属性注入，通过@autowired注入；
 */
@Configuration
@EnableConfigurationProperties(JedisProperties.class)
public class RedisConfig {

    @Autowired
    private JedisProperties jedisProperties;

    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(jedisProperties.getMaxTotal());
        config.setMaxIdle(jedisProperties.getMaxIdle());
        return new JedisPool(config, jedisProperties.getHost(), jedisProperties.getPort(), 1000, jedisProperties.getPassword());
    }
}
