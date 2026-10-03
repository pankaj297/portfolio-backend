package com.myportfolio.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor 
public class MySkillsResponseDto {
    
    private Long id;
    private String skill;
    private String category;
    private String level;
    private Integer yearsOfExperience;

    private String iconImg;

    private String iconPublicId;

    private Integer displayOrder;
    private Boolean isActive;

}
