package com.kp.foodinfo.controller;

import com.kp.foodinfo.service.GameService;
import com.kp.foodinfo.vo.PriceGameVoList;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GameController {

    private final GameService gameService;

    @GetMapping("/pricegame")
    public PriceGameVoList priceGameProcess(@RequestParam("price") int price) {

        return gameService.getPriceGameList(price);
    }
}
