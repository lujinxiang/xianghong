package com.xianghong.life.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: ServiceConfig
 * @Description:
 * @Author: lujinxiang
 * @Date: 2021-07-26
 */
@Slf4j
@MapperScan("com.xianghong.life.mapper")
@Configuration
public class MybatisConfig {
}
