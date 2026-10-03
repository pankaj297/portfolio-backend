package com.myportfolio.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor 
public class ProjectsResponseDto {

    private Long id;
    private String title;
    private String shortDescription;
    private String description;

    private String thumbnailImg;
    private String thumbnailPublicId;

    private String githubUrl;
    private String liveUrl;
    private String startDate;
    private String endDate;
    private String status;
    private Boolean featured;
    private Integer displayOrder;

}
