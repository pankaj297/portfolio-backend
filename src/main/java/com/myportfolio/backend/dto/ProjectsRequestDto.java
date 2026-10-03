package com.myportfolio.backend.dto;

import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data 
public class ProjectsRequestDto {

     @NotBlank(message = "Title can not be blank")
    @Size(max = 200, message = "Title cannot exceed 200 characters")
     private String title;
    
     @NotBlank(message = "shortDescription can not be blank")
     @Size(max = 500, message = "shortDescription must be  maximum 250 characters")
     private String shortDescription;
    
     @NotBlank(message = "description can not be blank")
     @Size(max = 200, message = "description must be  maximum 500 characters")
     private String description;
    
     private MultipartFile thumbnailImg;

     @URL(message = "Invalid github url")
     private String githubUrl;
    
     @URL(message = "Invalid live url")
     private String liveUrl;

     @NotNull(message = "Start date is required")
     private String startDate;
    
    private String endDate;

    @NotBlank(message = "Status is required")
    private String status;

    @NotNull(message = "Featured status is required")
    private Boolean featured;

    @NotNull(message = "Display order is required")
    @PositiveOrZero(message = "Display order can not be negative")
    private Integer displayOrder;


}
