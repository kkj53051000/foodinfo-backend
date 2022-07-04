package com.kp.foodinfo.service;

import com.kp.foodinfo.repository.FoodSpecialCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodSpecialCategoryService {
    private final FoodSpecialCategoryRepository foodSpecialCategoryRepository;
}
