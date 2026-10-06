package com.myportfolio.backend.dto;


import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data 
public class ResumeRequestDto {
        @NotBlank(message = "Title cannot be blank")
    @Size(max = 200, message = "Title cannot exceed 200 characters")
    private String title;

    private MultipartFile resumeFileUrl;

    @NotBlank(message = "Version cannot be blank")
    @Size(max = 50, message = "Version cannot exceed 50 characters")
    private String version;

    @NotNull(message = "Primary status is required")
    private Boolean isPrimary;

    private Long profileId;
    
    
}
