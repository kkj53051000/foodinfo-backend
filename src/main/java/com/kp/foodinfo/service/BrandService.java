package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.Food;
import com.kp.foodinfo.exception.DbNotFoundException;
import com.kp.foodinfo.dto.BrandDto;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.repository.FoodRepository;
import com.kp.foodinfo.util.StringToDateUtil;
import com.kp.foodinfo.vo.BrandVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BrandService {
    private final BrandRepository brandRepository;

    private final FoodRepository foodRepository;

    private final FileService fileService;

    public void saveBrand(BrandDto brandDto) throws IOException {
        log.info("saveBrand() : in");
        log.info("saveBrand() - FoodRepository - findById() : run");
        Food food = foodRepository.findById(brandDto.getFood_id()).get();

        log.info("saveBrand() - FileService - s3UploadProcess() : run");
        String clientPath = fileService.s3UploadProcess(brandDto.getFile());

        Brand brand = new Brand(brandDto.getName(), clientPath, StringToDateUtil.dateToDateProcess(new Date()), food);

        log.info("saveBrand() - BrandRepository - save() : run");
        brandRepository.save(brand);
    }

    public BrandVo getBrand(long brand_id) {
        log.info("getBrand() : in");
        log.info("getBrand() - BrandRepository - findById() : run");
        Brand brand = brandRepository.findById(brand_id).get();

        log.info("getBrand() : BrandVo return");
        return new BrandVo(brand);
    }

    public List<Brand> getBrandList(long food_id) {
        log.info("getBrandList() : in");
        log.info("getBrandList() - FoodRepository - findById() : run");
        Food food = foodRepository.findById(food_id).get();

        log.info("getBrandList() - BrandRepository - findByFood() : run");
        List<Brand> brands = brandRepository.findByFood(food);

        if(brands.size() == 0){
            log.error("DbNotFoundException() - brands.size() == 0");
            throw new  DbNotFoundException();
        }

        log.info("getBrandList() : List<Brand> return");
       return brands;
    }
}
