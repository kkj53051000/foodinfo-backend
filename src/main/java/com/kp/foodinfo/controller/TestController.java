package com.kp.foodinfo.controller;

import com.kp.foodinfo.vo.TestVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
// @RequestMapping("/api")
public class TestController {

    @GetMapping("${api.url}/testapi")
    public String testapiProcess(){
        return "testtttttttt";
    }

    @GetMapping("/testreact")
    public TestVo reacttestapi(){

        TestVo testVo = new TestVo("ksj", 23);

        System.out.println("in!!");

        return testVo;
    }
}
