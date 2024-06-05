package com.example.FinalProject.controller;

import com.example.FinalProject.dto.BonusHistoryDto;
import com.example.FinalProject.service.BonusHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bonus-history")
public class BonusHistoryController {
    private final BonusHistoryService bonusHistoryService;
    public BonusHistoryController(BonusHistoryService bonusHistoryService) {
        this.bonusHistoryService = bonusHistoryService;
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BonusHistoryDto>> getBonusHistoryForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(bonusHistoryService.getBonusHistoryByUserId(userId));
    }
    @GetMapping("/user/{userId}/range")
    public ResponseEntity<List<BonusHistoryDto>> getBonusHistoryForUserInDateRange(@PathVariable Long userId,
                                                                                   @RequestParam LocalDateTime startDate,
                                                                                   @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(bonusHistoryService.getBonusHistoryForUserInDateRange(userId, startDate, endDate));
    }
}
