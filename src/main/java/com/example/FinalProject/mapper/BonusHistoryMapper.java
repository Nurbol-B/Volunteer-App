package com.example.FinalProject.mapper;

import com.example.FinalProject.dto.BonusHistoryDto;
import com.example.FinalProject.dto.SocialTaskDto;
import com.example.FinalProject.entity.BonusHistory;
import com.example.FinalProject.entity.SocialTask;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BonusHistoryMapper {
    BonusHistory toEntity(BonusHistoryDto bonusHistoryDto);

    BonusHistoryDto toDto(BonusHistory bonusHistory);
    List<BonusHistoryDto> toDtoList(List<BonusHistory> bonusHistories);
}
