package com.myportfolio.backend.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor  
public class MyProfileResponseDto {

    private Long id;
    private String fullName;
    private String profileImg;
    private String profileImgPublicId;
    private String headline;
    private String bio;
    private String resumeUrl;
    private String resumePublicId;
    private String location;
    private String email;
    private String phone;
    private String githubUrl;
    private String linkedinUrl;
    private String portfolioUrl;
    private String leedcodeUrl;
    private boolean availableForWork;
   

    private List<CertificationsResponseDto> certifications;
}
