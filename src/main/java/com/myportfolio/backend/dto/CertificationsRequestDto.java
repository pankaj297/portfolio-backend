package com.myportfolio.backend.dto;

import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data 
public class CertificationsRequestDto {
    
    
    @NotBlank(message = "Certification name cannot be blank")
    @Size(
        max = 200,
        message = "Certification name cannot exceed 200 characters"
    )
    private String name;


    @NotBlank(message = "Organization cannot be blank")
    @Size(
        max = 200,
        message = "Organization cannot exceed 200 characters"
    )
    private String organization;


    @NotBlank(message = "Issue date is required")
    private String issueDate;


    @Size(
        max = 100,
        message = "Credential ID cannot exceed 100 characters"
    )
    private String credentialCode;


    @URL(message = "Invalid credential URL")
    private String credentialUrl;


    
    private MultipartFile certificateImage;


    @Size(
        max = 1000,
        message = "Description cannot exceed 1000 characters"
    )
    private String description;


    @Size(
        max = 500,
        message = "Skills cannot exceed 500 characters"
    )
    private String skills;

    // Profile ID
    private Long profileId;
    

}
