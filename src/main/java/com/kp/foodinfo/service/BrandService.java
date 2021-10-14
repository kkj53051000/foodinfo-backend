package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.exception.DbNotFoundException;
import com.kp.foodinfo.dto.BrandDto;
import com.kp.foodinfo.repository.BrandRepository;
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

    private final FileService fileService;

    public void saveBrand(BrandDto brandDto, String realPath) {

        String clientPath = fileService.imageUploadProcess(brandDto.getFile(), realPath);

        Brand brand = new Brand(brandDto.getName(), clientPath);

        brandRepository.save(brand);
    }

    public List<Brand> getBrandList() {

        List<Brand> brands = brandRepository.findAll();

        if(brands.size() == 0){
            throw new  DbNotFoundException();
        }

       return brands;
    }
}
