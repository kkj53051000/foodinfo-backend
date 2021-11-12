package com.kp.foodinfo.controller;

import com.kp.foodinfo.exception.BrandMenuKindPriorityOverlapException;
import com.kp.foodinfo.exception.UserNotFoundException;
import com.kp.foodinfo.vo.BasicVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserNotFoundException.class)
    public BasicVo UserNotFoundHandler(UserNotFoundException e) {
        System.out.println(">>>>>> " + e.code);
        return new BasicVo("failure", "userNotFound");
    }

    @ExceptionHandler(BrandMenuKindPriorityOverlapException.class)
    public BasicVo BrandMenuKindPriorityOverlapHandler() {
        return new BasicVo("failure", "brandMenuKindPriorityOverlap");
    }
}
