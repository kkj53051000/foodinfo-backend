package com.kp.foodinfo.controller;

import com.kp.foodinfo.domain.Item;
import com.kp.foodinfo.repository.ItemRepository;
import com.kp.foodinfo.vo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class PersonController {
    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/p")
    @Cacheable("PersonController.getTest()")
    public Person test() {
        System.out.println("get dooli");
        return new Person("dooli");
    }

    @GetMapping("/person")
    @Cacheable("PersonController.getPerson()")
    public String getPerson() throws InterruptedException {
        System.out.println("start");

        Thread.sleep(5000);

        System.out.println("end");

        return "person1";
    }

//    @PostConstruct
//    public void init() {
//        for(int i = 0; i < 30; i++) {
//            Item item = new Item();
//            item.setTitle("title" + i);
//            itemRepository.save(item);
//        }
//    }

    /*
     * 1. @Cacheable cache
     * 2. @Cacheable parameter cache
     * 3. @Cacheable parameter bounded cache
     * 4. @CacheEvict
     * */

    @GetMapping("/items2")
    @Cacheable(value = "getItems", key = "{#age}")
    public List<Item> getItems(@RequestParam("age") int age, @RequestParam("name") String name) {
        return itemRepository.findAll();
    }

    @GetMapping("/create-item")
    @CacheEvict(value = "getItems", allEntries = true)
    public String createItem(@RequestParam String title) {
        Item item = new Item();
        item.setTitle(title);

        itemRepository.save(item);

        return "success";
    }
}
