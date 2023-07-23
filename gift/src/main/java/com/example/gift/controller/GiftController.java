package com.example.gift.controller;

import com.example.gift.request.GiftFcfsRequest;
import com.example.gift.service.GiftService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gift")
public class GiftController {

    private final GiftService giftService;

    public GiftController(GiftService giftService) {
        this.giftService = giftService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> giftGame(@RequestBody GiftFcfsRequest giftFcfsRequest) {
        giftService.firstComeFirstServedGiftDistribution(giftFcfsRequest.getUserId(),
                giftFcfsRequest.getGiftId(), giftFcfsRequest.getGiftStock());


        return ResponseEntity.ok("선착순 게임 달려갑니다.");
    }
}
