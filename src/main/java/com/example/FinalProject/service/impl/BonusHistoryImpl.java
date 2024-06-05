package com.example.FinalProject.service.impl;

import com.example.FinalProject.dto.BonusHistoryDto;
import com.example.FinalProject.entity.BonusHistory;
import com.example.FinalProject.mapper.BonusHistoryMapper;
import com.example.FinalProject.repository.BonusHistoryRepository;
import com.example.FinalProject.service.BonusHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BonusHistoryImpl implements BonusHistoryService {
    private final BonusHistoryRepository bonusHistoryRepository;
    private final BonusHistoryMapper bonusHistoryMapper;

    @Override
    public BonusHistory addBonusHistory(Long userId, BigDecimal amount, BigDecimal balanceBefore, BigDecimal balanceAfter, String description) {
        BonusHistory bonusHistory = new BonusHistory();
        bonusHistory.setUserId(userId);
        bonusHistory.setAmount(amount);
        bonusHistory.setBalanceBefore(balanceBefore);
        bonusHistory.setBalanceAfter(balanceAfter);
        bonusHistory.setDescription(description);
        bonusHistory.setTimestamp(LocalDateTime.now());
        return bonusHistoryRepository.save(bonusHistory);
    }

    @Override
    public List<BonusHistoryDto> getBonusHistoryByUserId(Long userId) {
        List<BonusHistory> history = bonusHistoryRepository.findByUserId(userId);
        return history.stream()
                .map(bonusHistoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BonusHistoryDto> getBonusHistoryForUserInDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return bonusHistoryRepository.findByUserIdAndTimestampBetween(userId, startDate, endDate);
    }
}
