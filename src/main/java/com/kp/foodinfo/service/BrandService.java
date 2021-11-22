package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.Food;
import com.kp.foodinfo.exception.DbNotFoundException;
import com.kp.foodinfo.dto.BrandDto;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    private final FoodRepository foodRepository;

    private final FileService fileService;

    public void saveBrand(BrandDto brandDto) throws IOException {

        Food food = foodRepository.findById(brandDto.getFood_id()).get();

        String clientPath = fileService.s3UploadProcess(brandDto.getFile());

        Brand brand = new Brand(brandDto.getName(), clientPath, food);

        brandRepository.save(brand);
    }

    public List<Brand> getBrandList(long food_id) {

        Food food = foodRepository.findById(food_id).get();

        List<Brand> brands = brandRepository.findByFood(food);

        if(brands.size() == 0){
            throw new  DbNotFoundException();
        }

       return brands;
    }
}
