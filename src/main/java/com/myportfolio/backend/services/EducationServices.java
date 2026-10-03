package com.myportfolio.backend.services;

import java.util.List;


import com.myportfolio.backend.dto.EducationRequestDto;
import com.myportfolio.backend.dto.EducationResponseDto;

public interface EducationServices {

    List<EducationResponseDto> getAllEducation();

    EducationResponseDto getEducationById(Long id);

    EducationResponseDto createEducation(EducationRequestDto educationRequestDto);

    EducationResponseDto getEducation(Long id, EducationRequestDto educationRequestDto);

    void deleteEducationById(Long id);
    
}
