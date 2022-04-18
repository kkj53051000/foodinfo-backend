package com.kp.foodinfo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    Logger logger = LoggerFactory.getLogger(LogAspect.class);

    //    @Around("execution(* com.kp.foodinfo..*.*(..))")
//    @Around("@annotation(LogStartEnd)")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("start - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName());
        Object result = pjp.proceed();
        logger.info("finished - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName());
        return result;
    }

    @Around("@annotation(LogExcutionTime)")
    public Object excutionTimeLog(ProceedingJoinPoint pjp) throws Throwable {

        long beforeTime = System.currentTimeMillis();

        Object result = pjp.proceed();

        long afterTime = System.currentTimeMillis();

        logger.info("Excution Time : " + (afterTime - beforeTime));

        return result;

    }
}
