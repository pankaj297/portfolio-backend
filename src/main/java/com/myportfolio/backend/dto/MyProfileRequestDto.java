package com.myportfolio.backend.dto;

import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MyProfileRequestDto {

    @NotBlank(message = "Name can not be blank")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String fullName;

    private MultipartFile profileImg;

    @NotBlank(message = "Headline can not be blank")
    @Size(max = 150, message = "Headline cannot exceed 150 characters")
    private String headline;

    @NotBlank(message = "Bio can not be blank")
    @Size(max = 1000, message = "Bio cannot exceed 1000 characters")
    private String bio;

    private MultipartFile resume;

    @NotBlank(message = "Location can not be blank")
    @Size(max = 200, message = "Location cannot exceed 200 characters")
    private String location;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Invalid mobile number")
    private String phone;

    @URL(message = "Invalid github url")
    private String githubUrl;

    @URL(message = "Invalid linkedin url")
    private String linkedinUrl;

    @URL(message = "Invalid Portfolio url")
    private String portfolioUrl;

    @URL(message = "Invalid LeedCode url")
    private String leedcodeUrl;

    private boolean availableForWork;
}