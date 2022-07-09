package com.xianghong.life.aspect;

import com.xianghong.life.annotation.SelfLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author jinxianglu
 * @description 日志切面类
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Around("@annotation(com.xianghong.life.annotation.SelfLog)&&@annotation(selfLog)")
    public Object handleMethod(ProceedingJoinPoint pjd, SelfLog selfLog) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String className = pjd.getTarget().getClass().getSimpleName();
        String methodName = pjd.getSignature().getName();
        String selfLogValue = selfLog.value();
        log.info("[selfLog start],className={},methodName={},selfLog={}", className, methodName, selfLogValue);
        Object result = pjd.proceed();
        stopWatch.stop();
        log.info("[selfLog end],time cost:{}", stopWatch.getTotalTimeSeconds());
        return result;
    }
    
}
