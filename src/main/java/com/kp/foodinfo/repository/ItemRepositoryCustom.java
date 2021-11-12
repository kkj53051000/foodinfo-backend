package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.Item;

import java.util.List;

public interface ItemRepositoryCustom {
    List<Item> findMyItems();
}
