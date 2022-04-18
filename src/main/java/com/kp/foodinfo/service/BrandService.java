package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.Food;
import com.kp.foodinfo.exception.DbNotFoundException;
import com.kp.foodinfo.dto.BrandDto;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.repository.FoodRepository;
import com.kp.foodinfo.util.DateFormatUtil;
import com.kp.foodinfo.vo.BrandVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
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
        Food food = foodRepository.findById(brandDto.getFood_id()).get();

        String clientPath = fileService.s3UploadProcess(brandDto.getFile());

        Brand brand = new Brand(brandDto.getName(), clientPath, DateFormatUtil.dateToDateProcess(new Date()), food);

        brandRepository.save(brand);
    }

    public BrandVo getBrand(long brand_id) {
        Brand brand = brandRepository.findById(brand_id).get();

        return new BrandVo(brand);
    }

    public List<Brand> getBrandList(long food_id) {
        Food food = foodRepository.findById(food_id).get();

        List<Brand> brands = brandRepository.findByFood(food);

        if (brands.size() == 0) {
            throw new DbNotFoundException();
        }

        return brands;
    }
}
