package com.myportfolio.backend.dto;




import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor 
public class ResumeResponseDto {
    

    private Long id;
    private String title;
    
    private String resumeFileUrl;

    private String resumePublicId;

    private String version;
    private Boolean isPrimary;
    private LocalDateTime uploadedAt;

}
