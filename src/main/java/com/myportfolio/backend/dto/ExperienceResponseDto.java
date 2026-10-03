package com.myportfolio.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor 
public class ExperienceResponseDto {

    private Long id;
    private String companyName;
    private String position;
    private String employmentType;
    private String location;
    private String startDate;
    private String endDate;
    private Boolean currentlyWorking;

    private String companyLogo;
    private String companyPublicId;

    private String companyUrl;
    private String description;
    private Integer displayOrder;
}
