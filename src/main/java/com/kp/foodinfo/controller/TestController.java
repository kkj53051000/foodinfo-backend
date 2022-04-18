package com.kp.foodinfo.controller;

import com.kp.foodinfo.domain.Item;
import com.kp.foodinfo.dto.ExcelMenuDto;
import com.kp.foodinfo.repository.ItemRepository;
import com.kp.foodinfo.util.ReturnStatus;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.TestVo;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
// @RequestMapping("/api")
public class TestController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/api/testapi")
    public String testapiProcess() {
        return "testtttttttt";
    }

    @GetMapping("/testreact")
    public TestVo reacttestapi() {

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

        if (items == null) {
            System.out.println("no cache read db...");
            items = itemRepository.findAll();
            valueOperations.set("TestController.testRedis()", items);
        } else {
            System.out.println("cache hit!");
        }

        long end = System.currentTimeMillis();


        System.out.println("시간 : " + (end - start));

        return items;
    }


    @PostMapping("/exceltest")
    public BasicVo testExcel(@RequestPart(value = "file", required = true) MultipartFile file) throws IOException {

        List<ExcelMenuDto> excelMenuDtos = new ArrayList<>();

        String extension = FilenameUtils.getExtension(file.getOriginalFilename()); // 3

        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            System.out.printf("에러");
        }

        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4

            Row row = worksheet.getRow(i);

            System.out.printf("\n num : " + row.getCell(0).getNumericCellValue());
            System.out.printf(" name : " + row.getCell(1).getStringCellValue());
            System.out.printf(" type : " + row.getCell(2).getStringCellValue());
            System.out.printf(" size : " + row.getCell(3).getStringCellValue());
            System.out.printf(" price : " + row.getCell(4).getNumericCellValue());
            System.out.printf(" img : " + row.getCell(5).getStringCellValue());
        }

        return new BasicVo(ReturnStatus.success);

    }
}
