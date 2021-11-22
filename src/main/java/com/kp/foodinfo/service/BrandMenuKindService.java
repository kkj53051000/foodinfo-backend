package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.BrandMenuKind;
import com.kp.foodinfo.exception.BrandMenuKindPriorityOverlapException;
import com.kp.foodinfo.request.BrandMenuKindRequest;
import com.kp.foodinfo.repository.BrandMenuKindRepository;
import com.kp.foodinfo.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BrandMenuKindService {
    @Autowired
    private BrandMenuKindRepository brandMenuKindRepository;

    @Autowired
    private BrandRepository brandRepository;

    public void saveBrandMenuKind(BrandMenuKindRequest brandMenuKindRequest) {

        Brand brand = brandRepository.findById(brandMenuKindRequest.getBrand_id()).get();

        BrandMenuKind brandMenuKind = new BrandMenuKind(brandMenuKindRequest.getName(), brandMenuKindRequest.getPriority(), brand);

        brandMenuKindRepository.save(brandMenuKind);
    }

    public List<BrandMenuKind> getBrandMenuKinds(long brand_id) {

        Brand brand = brandRepository.findById(brand_id).get();

        List<BrandMenuKind> brandMenuKinds = brandMenuKindRepository.findByBrand(brand);

        return brandMenuKinds;
    }
}
