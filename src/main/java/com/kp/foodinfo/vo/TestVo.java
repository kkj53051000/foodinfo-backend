package com.kp.foodinfo.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class TestVo {
    private String name;
    private int age;

    public TestVo(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
