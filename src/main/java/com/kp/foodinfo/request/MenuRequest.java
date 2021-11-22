package com.kp.foodinfo.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class MenuRequest {
    private String name;
    private int price;
    private long brandMenuKind_id;

    public MenuRequest(String name, int price, long brandMenuKind_id) {
        this.name = name;
        this.price = price;
        this.brandMenuKind_id = brandMenuKind_id;
    }
}
