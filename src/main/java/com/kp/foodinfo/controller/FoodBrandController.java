package com.kp.foodinfo.controller;

import com.kp.foodinfo.service.FoodBrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FoodBrandController {
    private final FoodBrandService foodBrandService;
}
