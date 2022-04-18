package com.kp.foodinfo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.PostConstruct;

public class RedisAspect {
    @Autowired
    RedisTemplate redisTemplate;

    ValueOperations<String, Object> valueOperations;

//    @PostConstruct
//    public void init() {
//        valueOperations = redisTemplate.opsForValue();
//    }
//
//    @Around("@annotation(LogExcutionTime)")
//    public Object excutionTimeLog(ProceedingJoinPoint pjp) throws Throwable {
//        String methodName = pjp.getSignature().getName();
//
//        Object obj = valueOperations.get(methodName);
//        if(obj == null) {
//            Object result = pjp.proceed();
//            valueOperations.set(methodName, result);
//            return result;
//        } else {
//            return obj;
//        }
//    }
}
