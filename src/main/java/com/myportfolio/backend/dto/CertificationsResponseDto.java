package com.myportfolio.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor 
public class CertificationsResponseDto {

    private Long id;
    
    private String name;
    private String organization;
    private String issueDate;
    private String credentialCode;
    private String credentialUrl;

    private String certificateImage;
    private String certificateImagePublicId;

    private String description;
    private String skills;

    private Long profileId;
    private String profileName;

}
