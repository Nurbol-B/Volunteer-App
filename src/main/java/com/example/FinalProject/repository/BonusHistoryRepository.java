package com.example.FinalProject.repository;

import com.example.FinalProject.entity.BonusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BonusHistoryRepository extends JpaRepository <BonusHistory,Long> {
    List<BonusHistory> findByUserId(Long userId);
}
