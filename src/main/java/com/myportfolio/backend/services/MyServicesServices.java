package com.myportfolio.backend.services;

import java.util.List;


import com.myportfolio.backend.dto.MyServicesRequestDto;
import com.myportfolio.backend.dto.MyServicesResponseDto;


public interface MyServicesServices {
         
    List<MyServicesResponseDto> getAllServices();

    MyServicesResponseDto getServicesById(Long id);

    MyServicesResponseDto createServices(MyServicesRequestDto myServicesRequestDto);

    MyServicesResponseDto updateServices(Long id, MyServicesRequestDto myServicesRequestDto);

    void deleteServicesById(Long id);
    
}
