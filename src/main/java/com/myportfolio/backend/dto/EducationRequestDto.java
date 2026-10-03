package com.myportfolio.backend.dto;

import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data 
public class EducationRequestDto {
    
   @NotBlank(message = "Degree cannot be blank")
    @Size(max = 150, message = "Degree cannot exceed 150 characters")
    private String degree;


    @NotBlank(message = "Field of study cannot be blank")
    @Size(max = 150, message = "Field of study cannot exceed 150 characters")
    private String fieldOfStudy;


    @NotBlank(message = "Institution cannot be blank")
    @Size(max = 200, message = "Institution cannot exceed 200 characters")
    private String institution;


    @NotBlank(message = "Location cannot be blank")
    @Size(max = 200, message = "Location cannot exceed 200 characters")
    private String location;


    @NotBlank(message = "Start date is required")
    private String startDate;


    private String endDate;


    @NotNull(message = "Currently studying status is required")
    private Boolean currentlyStudying;


    @Size(max = 50, message = "Grade cannot exceed 50 characters")
    private String grade;


    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    
    private MultipartFile institutionLogo;


    @URL(message = "Invalid institution URL")
    private String institutionUrl;

}
