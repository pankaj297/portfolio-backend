package com.myportfolio.backend.services;

import java.util.List;

import com.myportfolio.backend.dto.ResumeRequestDto;
import com.myportfolio.backend.dto.ResumeResponseDto;

public interface ResumeServices {

    List<ResumeResponseDto> getAllResumes();

    ResumeResponseDto getResumeById(Long id);

    ResumeResponseDto createResume(ResumeRequestDto resumeRequestDto);

    ResumeResponseDto updateResume(
            Long id,
            ResumeRequestDto resumeRequestDto);

    void deleteResumeById(Long id);
}