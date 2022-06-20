package com.xianghong.life;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class FeedsAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeedsAdminApplication.class, args);
    }

}

