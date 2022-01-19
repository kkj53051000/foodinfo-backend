package com.kp.foodinfo.controller;

import com.kp.foodinfo.exception.*;
import com.kp.foodinfo.vo.BasicVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(UserNotFoundException.class)
    public BasicVo userNotFoundHandler(UserNotFoundException e) {
        System.out.println(">>>>>> " + e.code);
        return new BasicVo("failure", "userNotFound");
    }

    @ExceptionHandler(BrandMenuKindPriorityOverlapException.class)
    public BasicVo brandMenuKindPriorityOverlapHandler() {
        return new BasicVo("failure", "brandMenuKindPriorityOverlap");
    }

    @ExceptionHandler(JwtVerifyFailException.class)
    public BasicVo jwtVerifyFailHandler() {
        log.error("JwtVerifyFailHandler() : in");
        return new BasicVo("failure", "JwtVerifyFail");
    }

    @ExceptionHandler(DbNotFoundException.class)
    public BasicVo dbNotFoundHandler() {
        log.error("DbNotFoundException() : in");
        return new BasicVo("failure", "DbNotFound");
    }

    @ExceptionHandler(FollowCheckException.class)
    public BasicVo followCheckHandler() {
        return new BasicVo("failure", "FollowChekcException");
    }

}
