package com.kp.foodinfo.service;

import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    public String getMemberName() {
        // http request... like axios

        return "my name";
    }
}
