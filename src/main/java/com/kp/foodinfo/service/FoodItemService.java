package com.kp.foodinfo.service;

import com.kp.foodinfo.repository.FoodItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodItemService {
    private final FoodItemRepository foodItemRepository;
}
