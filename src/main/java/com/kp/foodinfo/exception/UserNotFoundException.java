package com.kp.foodinfo.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserNotFoundException extends RuntimeException {
    public String code;

    public UserNotFoundException(String code) {
        this.code = code;
    }
}
