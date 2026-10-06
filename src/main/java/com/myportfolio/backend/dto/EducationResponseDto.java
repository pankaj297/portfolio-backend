package com.myportfolio.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor 
public class EducationResponseDto {
    
    private Long id;
    private String degree;
    private String fieldOfStudy;
    private String institution;
    private String location;
    private String startDate;
    private String endDate;
    private Boolean currentlyStudying;
    private String grade;
    private String description;

    private String institutionLogo;
    private String institutionLogoPublicId;

    private String institutionUrl;


    private Long profileId;
    private String profileName;

}
