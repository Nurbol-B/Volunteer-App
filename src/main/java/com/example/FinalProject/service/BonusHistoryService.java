package com.example.FinalProject.service;

import com.example.FinalProject.dto.BonusHistoryDto;
import com.example.FinalProject.entity.BonusHistory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface BonusHistoryService {
    BonusHistory addBonusHistory(Long userId, BigDecimal amount, BigDecimal balanceBefore, BigDecimal balanceAfter, String description);
    List<BonusHistoryDto> getBonusHistoryByUserId(Long userId);
    List<BonusHistoryDto> getBonusHistoryForUserInDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);
}
