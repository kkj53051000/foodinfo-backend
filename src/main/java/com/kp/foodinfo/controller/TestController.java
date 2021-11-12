package com.kp.foodinfo.controller;

import com.kp.foodinfo.domain.Item;
import com.kp.foodinfo.repository.ItemRepository;
import com.kp.foodinfo.vo.TestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
// @RequestMapping("/api")
public class TestController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ItemRepository itemRepository;

    @GetMapping("api/testapi")
    public String testapiProcess(){
        return "testtttttttt";
    }

    @GetMapping("/testreact")
    public TestVo reacttestapi(){

        TestVo testVo = new TestVo("ksj", 23);

        System.out.println("in!!");

        return testVo;
    }

    @PostMapping("/testdate")
    public String testDate(String date, String time) throws ParseException {
        System.out.println("Date : " + date + "Time : " + time);

        String requestDateStr = date + " " + time;

        System.out.println("requestDateStr : " + requestDateStr);

        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date requestDate = fm.parse(requestDateStr);

        System.out.println("requestDate : " + requestDate);

        return "success";
    }

//    @PostConstruct
//    @Transactional
//    public void init() {
//        for(int i = 0; i < 100; i++) {
//            Item item = new Item();
//            item.setTitle("title" + i);
//
//            itemRepository.save(item);
//        }
//    }

    @GetMapping("/testredis")
    public List<Item> testRedis() {
        long start = System.currentTimeMillis();

        ValueOperations<String, List<Item>> valueOperations = redisTemplate.opsForValue();

        List<Item> items = valueOperations.get("TestController.testRedis()");

        if(items == null) {
            System.out.println("no cache read db...");
            items = itemRepository.findAll();
            valueOperations.set("TestController.testRedis()", items);
        }else {
            System.out.println("cache hit!");
        }

        long end = System.currentTimeMillis();


        System.out.println("시간 : " + (end - start));

        return items;
    }
}
