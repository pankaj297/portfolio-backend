package com.myportfolio.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor 
public class ContactResponseDto {

    private Long id;
    private String name;
    private String email;
    private String subject;
    private String message;

}
