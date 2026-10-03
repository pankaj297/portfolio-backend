package com.myportfolio.backend.services;

import java.util.List;


import com.myportfolio.backend.dto.AchievementsRequestDto;
import com.myportfolio.backend.dto.AchievementsResponseDto;

public interface AchievementsServices {

    List<AchievementsResponseDto> getAllAchievements();

    AchievementsResponseDto getAchievementsById(Long id);

    AchievementsResponseDto createAchievements(AchievementsRequestDto achievementsRequestDto);

    AchievementsResponseDto updateAchievements(Long id, AchievementsRequestDto achievementsRequestDto);

    void deleteAchievementsById(Long id);
    
}
