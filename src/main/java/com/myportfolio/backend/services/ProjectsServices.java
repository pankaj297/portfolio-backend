package com.myportfolio.backend.services;

import java.util.List;
import java.util.Map;


import com.myportfolio.backend.dto.ProjectsRequestDto;
import com.myportfolio.backend.dto.ProjectsResponseDto;

public interface ProjectsServices {

    List<ProjectsResponseDto> getAllProjects();
    
    ProjectsResponseDto getProjectById(Long id);

    ProjectsResponseDto createProjects(ProjectsRequestDto projectsRequestDto);

    ProjectsResponseDto updateProjects(Long id, ProjectsRequestDto projectsRequestDto);

    void deleteProjectById(Long id);

    ProjectsResponseDto updatePartialProjects(Long id, Map<String,Object> updates);
    
}
