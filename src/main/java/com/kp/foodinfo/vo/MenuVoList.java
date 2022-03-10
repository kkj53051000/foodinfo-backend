package com.kp.foodinfo.vo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Data
public class MenuVoList implements Serializable {

    List<MenuVo> items = null;

    public MenuVoList(List<MenuVo> menuVos) {
        this.items = menuVos;
    }
}
