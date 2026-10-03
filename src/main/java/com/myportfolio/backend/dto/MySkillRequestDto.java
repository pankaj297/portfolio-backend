package com.myportfolio.backend.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MySkillRequestDto {

    @NotBlank(message = "Skill can not be blank")
    @Size(max = 100, message = "Skill cannot exceed 100 characters")
    private String skill;

    @NotBlank(message = "Category is required")
    @Size(max = 100, message = "Category cannot exceed 50 characters")
    private String category;


    @NotBlank(message = "Level is required")
    private String level;

    @NotNull(message = "Years of experience is required")
    @Min(value = 0, message = "Years of experience can not be negative")
    @Max(value = 50, message = "Years of experience can not be more than 50 ")
    private Integer yearsOfExperience;

    private MultipartFile iconImg;

    @NotNull(message = "Display order is required")
    @PositiveOrZero(message = "Display order can not be negative")
    private Integer displayOrder;

    @NotNull(message = "Active status is required")
    private Boolean isActive;

     
}
