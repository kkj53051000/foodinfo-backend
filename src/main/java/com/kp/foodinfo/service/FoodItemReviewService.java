package com.kp.foodinfo.service;

import com.kp.foodinfo.repository.FoodItemReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodItemReviewService {
    private final FoodItemReviewRepository foodItemReviewRepository;
}
