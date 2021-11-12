package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.BrandMenuKind;
import com.kp.foodinfo.exception.BrandMenuKindPriorityOverlapException;
import com.kp.foodinfo.request.BrandMenuKindRequest;
import com.kp.foodinfo.repository.BrandMenuKindRepository;
import com.kp.foodinfo.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BrandMenuKindService {
    private final BrandMenuKindRepository brandMenuKindRepository;

    private final BrandRepository brandRepository;

    public void saveBrandMenuKind(BrandMenuKindRequest brandMenuKindRequest) {
        //brandMenuKindRepository.findByPriority(brandMenuKindRequest.getPriority())
        //        .orElseThrow(() -> new BrandMenuKindPriorityOverlapException());

        Brand brand = brandRepository.getById(brandMenuKindRequest.getBrand_id());

        BrandMenuKind brandMenuKind = new BrandMenuKind(brandMenuKindRequest.getName(), brandMenuKindRequest.getPriority(), brand);

        brandMenuKindRepository.save(brandMenuKind);
    }
}
