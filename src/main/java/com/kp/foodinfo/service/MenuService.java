package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.BrandMenuKind;
import com.kp.foodinfo.domain.Menu;
import com.kp.foodinfo.exception.DbNotFoundException;
import com.kp.foodinfo.request.MenuRequest;
import com.kp.foodinfo.repository.BrandMenuKindRepository;
import com.kp.foodinfo.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    private final BrandMenuKindRepository brandMenuKindRepository;

    private final FileService fileService;

    public void saveMenu(MultipartFile file, MenuRequest menuRequest, String realPath) {

        String clientPath = fileService.imageUploadProcess(file, realPath);

        BrandMenuKind brandMenuKind = brandMenuKindRepository.findById(menuRequest.getBrandMenuKind_id())
                .get();

        Menu menu = new Menu(menuRequest.getName(), menuRequest.getPrice(), clientPath, brandMenuKind);

        menuRepository.save(menu);
    }

    public List<Menu> getMenuList(long menuKind_id){

        BrandMenuKind brandMenuKind = brandMenuKindRepository.findById(menuKind_id)
                .get();

        List<Menu> menus = menuRepository.findByBrandMenuKind(brandMenuKind);

        if(menus.size() == 0){
            throw new DbNotFoundException();
        }

        return menus;
    }
}
