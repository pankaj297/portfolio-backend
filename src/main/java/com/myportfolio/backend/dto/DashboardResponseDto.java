package com.myportfolio.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor  
public class DashboardResponseDto {

    private Long projects;
    private Long skills;
    private Long experiences;
    private Long educations;
    private Long certifications;
    private Long achievements;
    private Long services;
    private Long contact;
    private Long blogs;

}
