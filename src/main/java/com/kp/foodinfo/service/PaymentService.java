package com.kp.foodinfo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final MemberService memberService;

    public void payment() {
        String memberName = memberService.getMemberName();


        // member insert ... payment
    }
}
