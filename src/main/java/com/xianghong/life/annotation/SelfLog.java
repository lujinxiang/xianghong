package com.xianghong.life.annotation;

import java.lang.annotation.*;

/**
 * @author jinxianglu
 * @description 日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SelfLog {

    String value();

}
