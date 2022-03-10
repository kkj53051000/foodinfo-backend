package com.kp.foodinfo.controller;

import com.kp.foodinfo.domain.Item;
import com.kp.foodinfo.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;

@RestController
public class ItemController {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    EntityManager em;


    @GetMapping("/items")
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    @GetMapping("/items3")
    public Page<Item> getItems2(@RequestParam int page) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
        return itemRepository.findByPage(pageRequest);
    }

    @GetMapping("/items4")
    public Slice<Item> getItems3(@RequestParam int page) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
        return itemRepository.findBySlice(pageRequest);
    }

    @GetMapping("/item5")
    public List<Item> getItems4() {
        // itemCustomRepsotiroy.findMyItems();
        return itemRepository.findMyItems();
    }
}
