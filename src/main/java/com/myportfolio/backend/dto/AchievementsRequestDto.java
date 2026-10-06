package com.myportfolio.backend.dto;



import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data 
public class AchievementsRequestDto {
    
     @NotBlank(message = "Title cannot be blank")
    @Size(max = 200, message = "Title cannot exceed 200 characters")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotBlank(message = "Date cannot be blank")
    private String date;

    @NotBlank(message = "Organization cannot be blank")
    @Size(max = 200, message = "Organization cannot exceed 200 characters")
    private String organization;

    private MultipartFile achievementsImage;

    @NotNull(message = "Display order is required")
    @PositiveOrZero(message = "Display order cannot be negative")
    private Integer displayOrder;

    private Long profileId;
    

}
