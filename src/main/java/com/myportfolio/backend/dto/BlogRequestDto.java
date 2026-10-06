package com.myportfolio.backend.dto;


import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data 
public class BlogRequestDto {
    
      @NotBlank(message = "Title cannot be blank")
    @Size(max = 250, message = "Title cannot exceed 250 characters")
    private String title;

    @NotBlank(message = "Slug cannot be blank")
    @Size(max = 250, message = "Slug cannot exceed 250 characters")
    private String slug;

    @NotBlank(message = "Excerpt cannot be blank")
    @Size(max = 500, message = "Excerpt cannot exceed 500 characters")
    @Column(columnDefinition = "TEXT")
    private String excerpt;

    @NotBlank(message = "Content cannot be blank")
    private String content;
 
    private MultipartFile imgThumbnail;

    @NotBlank(message = "Category cannot be blank")
    @Size(max = 100, message = "Category cannot exceed 100 characters")
    private String category;

    @NotNull(message = "Published status is required")
    private Boolean published;

    private Long profileId;
    

    
}
