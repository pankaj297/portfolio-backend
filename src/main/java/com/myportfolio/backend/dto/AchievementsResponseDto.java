package com.myportfolio.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor 
public class AchievementsResponseDto {

    private Long id;
    private String title;
    private String description;
    private String date;
    private String organization;

    private String achievementsImage;
    private String achievementsImagePublicId;

    private Integer displayOrder;

    private Long profileId;
    private String profileName;

}
