package com.kp.foodinfo.vo;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;


@Builder
@Data
public class MenuVo implements Serializable {
    private long id;
    private String name;
    private String price;
    private List<MenuSizeVo> menuSizeVo;
    private String img;
}
