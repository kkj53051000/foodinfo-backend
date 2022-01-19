package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Menu;
import com.kp.foodinfo.domain.MenuSize;
import com.kp.foodinfo.domain.MenuSizeKind;
import com.kp.foodinfo.repository.MenuRepository;
import com.kp.foodinfo.repository.MenuSizeKindRepository;
import com.kp.foodinfo.repository.MenuSizeRepositroy;
import com.kp.foodinfo.request.MenuSizeRequest;
import com.kp.foodinfo.vo.BasicVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuSizeService {
    private final MenuSizeRepositroy menuSizeRepositroy;

    private final MenuRepository menuRepository;

    private final MenuSizeKindRepository menuSizeKindRepository;

    public BasicVo saveMenuSize(MenuSizeRequest menuSizeRequest) {

        Menu menu = menuRepository.findById(menuSizeRequest.getMenuId()).get();

        MenuSizeKind menuSizeKind = menuSizeKindRepository.findById(menuSizeRequest.getSizeId()).get();

        MenuSize menuSize = new MenuSize(menu, menuSizeKind, menuSizeRequest.getPrice());

        menuSizeRepositroy.save(menuSize);

        return new BasicVo("success");
    }
}
