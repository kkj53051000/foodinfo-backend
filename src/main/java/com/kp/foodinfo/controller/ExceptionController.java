package com.kp.foodinfo.controller;

import com.kp.foodinfo.exception.*;
import com.kp.foodinfo.util.ReturnStatus;
import com.kp.foodinfo.vo.BasicVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(UserNotFoundException.class)
    public BasicVo userNotFoundHandler(UserNotFoundException e) {
//        System.out.println(">>>>>> " + e.code);
        return new BasicVo(ReturnStatus.failure, "userNotFound");
    }

    @ExceptionHandler(BrandMenuKindPriorityOverlapException.class)
    public BasicVo brandMenuKindPriorityOverlapHandler() {
        return new BasicVo(ReturnStatus.failure, "brandMenuKindPriorityOverlap");
    }

    @ExceptionHandler(JwtVerifyFailException.class)
    public BasicVo jwtVerifyFailHandler() {
        log.error("JwtVerifyFailHandler() : in");
        return new BasicVo(ReturnStatus.failure, "JwtVerifyFail");
    }

    @ExceptionHandler(DbNotFoundException.class)
    public BasicVo dbNotFoundHandler(DbNotFoundException e) {
        log.error("DbNotFoundException() : in");
        return new BasicVo(ReturnStatus.failure, "DbNotFound");
    }

    @ExceptionHandler(FollowCheckException.class)
    public BasicVo followCheckHandler() {
        return new BasicVo(ReturnStatus.failure, "FollowChekcException");
    }

    @ExceptionHandler(UserExistsException.class)
    public BasicVo userExistsException() {
        log.error("DbNotFoundException() : in");
        return new BasicVo(ReturnStatus.failure, "UserExistsException");
    }

    @ExceptionHandler(SiktamEventEntryOverlapException.class)
    public BasicVo siktamEventEntryOverlapException() {
        return new BasicVo(ReturnStatus.failure, "overlap");
    }

    @ExceptionHandler(PriceGameMenuNotFondException.class)
    public BasicVo priceGameMenuNotFoundException() {
        log.error("priceGameMenuNotFoundException()");
        return new BasicVo(ReturnStatus.failure, "menuNotFound");
    }

    @ExceptionHandler(EventEntryLateException.class)
    public BasicVo eventEntryLateException() {
        return new BasicVo(ReturnStatus.failure, "eventEntryLate");
    }

    @ExceptionHandler(FollowAllContentEndException.class)
    public BasicVo followAllContentEndException() {
        return new BasicVo(ReturnStatus.failure, "allContentEnd");
    }

    @ExceptionHandler(UserEmailCheckFailExceotion.class)
    public BasicVo userEmailCheckFailExceotion() {
        return new BasicVo(ReturnStatus.failure, "emailCheckFail");
    }
}
