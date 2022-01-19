package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.MenuSizeKind;
import com.kp.foodinfo.repository.MenuSizeKindRepository;
import com.kp.foodinfo.request.MenuSizeKindRequest;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.MenuSizeKindListVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuSizeKindService {
    private final MenuSizeKindRepository menuSizeKindRepository;

    public BasicVo saveMenuSizeKind(MenuSizeKindRequest menuSizeKindRequest) {

        MenuSizeKind menuSizeKind = new MenuSizeKind(menuSizeKindRequest.getSize());

        menuSizeKindRepository.save(menuSizeKind);

        return new BasicVo("success");
    }

    public MenuSizeKindListVo getMenuSizeKindList() {
        List<MenuSizeKind> menuSizeKinds = menuSizeKindRepository.findAll();

        return new MenuSizeKindListVo(menuSizeKinds);
    }
}
