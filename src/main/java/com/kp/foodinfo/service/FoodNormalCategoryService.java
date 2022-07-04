package com.kp.foodinfo.service;

import com.kp.foodinfo.repository.FoodNormalCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodNormalCategoryService {
    private final FoodNormalCategoryRepository foodNormalCategoryRepository;
}
