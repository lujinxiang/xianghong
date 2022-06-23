package com.xianghong.life.redis;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jinxianglu
 */

@ConfigurationProperties(prefix = JedisProperties.JEDIS_PREFIX)
@Data
@Component
public class JedisProperties {

    public static final String JEDIS_PREFIX = "redis";

    private String host;

    private int port;

    private String password;

    private int timeout;

    private int maxActive;

    private int maxIdle;

    private int minIdle;

    private int maxTotal;

}
