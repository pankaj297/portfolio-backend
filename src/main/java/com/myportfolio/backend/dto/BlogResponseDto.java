package com.myportfolio.backend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor 
public class BlogResponseDto {
    
    private Long id;

    private String title;
    private String slug;
    private String excerpt;
    private String content;

    private String imgThumbnail;
    private String imgThumbnailPublicId;

    private String category;
    private Boolean published;
    private LocalDateTime publishedAt;

    private Long profileId;
    private String profileName;
    
}
