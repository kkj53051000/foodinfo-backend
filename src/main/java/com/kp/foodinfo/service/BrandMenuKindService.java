package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.BrandMenuKind;
import com.kp.foodinfo.exception.BrandMenuKindPriorityOverlapException;
import com.kp.foodinfo.request.BrandMenuKindRequest;
import com.kp.foodinfo.repository.BrandMenuKindRepository;
import com.kp.foodinfo.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BrandMenuKindService {
    private final BrandMenuKindRepository brandMenuKindRepository;

    private final BrandRepository brandRepository;

    public void saveBrandMenuKind(BrandMenuKindRequest brandMenuKindRequest) {
        log.info("saveBrandMenuKind() - in");
        log.info("saveBrandMenuKind() - BrandRepository - findById() run");
        Brand brand = brandRepository.findById(brandMenuKindRequest.getBrand_id()).get();

        BrandMenuKind brandMenuKind = new BrandMenuKind(brandMenuKindRequest.getName(), brandMenuKindRequest.getPriority(), brand);

        log.info("saveBrandMenuKind() - BrandMenuKindRepository - save() run");
        brandMenuKindRepository.save(brandMenuKind);
    }

    public List<BrandMenuKind> getBrandMenuKinds(long brand_id) {
        log.info("getBrandMenuKinds() - in");
        log.info("getBrandMenuKinds() - BrandRepository - findById() run");
        Brand brand = brandRepository.findById(brand_id).get();

        log.info("getBrandMenuKinds() - BrandMenuKindRepository - findByBrand() run");
        List<BrandMenuKind> brandMenuKinds = brandMenuKindRepository.findByBrand(brand);

        log.info("getBrandMenuKinds() - List<BrandMenuKind> return");
        return brandMenuKinds;
    }
}
