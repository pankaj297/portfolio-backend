package com.myportfolio.backend.dto;

import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data 
public class ExperienceRequestDto {

    @NotBlank(message = "Company name cannot be blank")
    @Size(max = 150, message = "Company name cannot exceed 150 characters")
    private String companyName;

    @NotBlank(message = "Position cannot be blank")
    @Size(max = 150, message = "Position cannot exceed 150 characters")
    private String position;

    @NotBlank(message = "Employment type cannot be blank")
    @Size(max = 50, message = "Employment type cannot exceed 50 characters")
    private String employmentType;

    @NotBlank(message = "Location cannot be blank")
    @Size(max = 150, message = "Location cannot exceed 150 characters")
    private String location;

    @NotBlank(message = "Start date cannot be blank")
    @Size(max = 20, message = "Start date cannot exceed 20 characters")
    private String startDate;

    @Size(max = 20, message = "End date cannot exceed 20 characters")
    private String endDate;

    @NotNull(message = "Currently working status is required")
    private Boolean currentlyWorking;

    
    private MultipartFile companyLogo;

    @URL(message = "Company URL must be a valid URL")
    @Size(max = 500, message = "Company URL cannot exceed 500 characters")
    private String companyUrl;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 3000, message = "Description cannot exceed 3000 characters")
    private String description;

    @NotNull(message = "Display order is required")
    @Min(value = 0, message = "Display order cannot be negative")
    @Max(value = 1000, message = "Display order cannot exceed 1000")
    private Integer displayOrder;

}
