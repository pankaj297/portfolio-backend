package com.myportfolio.backend.services;

import java.util.List;
import java.util.Map;



import com.myportfolio.backend.dto.ExperienceRequestDto;
import com.myportfolio.backend.dto.ExperienceResponseDto;

public interface ExperienceServices {

    List<ExperienceResponseDto> getAllExperience();

    ExperienceResponseDto getExperienceById(Long id);

    ExperienceResponseDto createExperience(ExperienceRequestDto experienceRequestDto);

    ExperienceResponseDto updateExperience(Long id, ExperienceRequestDto experienceRequestDto);

    void deleteExperienceById(Long id);

	 ExperienceResponseDto updatePartialExperience(Long id, Map<String,Object> updates);
    
}
