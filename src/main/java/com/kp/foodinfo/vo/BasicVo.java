package com.kp.foodinfo.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BasicVo {
    private String status;
    private String cause;
    private String items = null;

    public BasicVo(String status) {
        this.status = status;
    }

    public BasicVo(String status, String cause) {
        this.status = status;
        this.cause = cause;
    }
}

// Entity
class Order {
    private String orderStatus = "ORDERED";
    private int price = 100;

    public void cancel() {
        this.orderStatus = "CANCELED";
        this.price = 0;
        System.out.println(123);
    }
}

// order.cancel();