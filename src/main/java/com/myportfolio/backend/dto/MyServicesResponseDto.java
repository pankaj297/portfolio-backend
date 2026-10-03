package com.myportfolio.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor 
public class MyServicesResponseDto {
    
    private Long id;
    private String title;
    private String description;

    private Integer displayOrder;
    private Boolean isActive;
    
}
