package com.kp.foodinfo.vo;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class Person implements Serializable {
    private final String name;
}
