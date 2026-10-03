package com.myportfolio.backend.services;

import java.util.List;


import com.myportfolio.backend.dto.CertificationsRequestDto;
import com.myportfolio.backend.dto.CertificationsResponseDto;


public interface CertificationServices {

    List<CertificationsResponseDto> getAllCertification();

 
    CertificationsResponseDto getCertificationById(Long id);

   
    CertificationsResponseDto createCertification(CertificationsRequestDto certificationsRequestDto);

 
    CertificationsResponseDto updateCertification(Long id, CertificationsRequestDto certificationsRequestDto);

    void deleteCertificationById(Long id);
    
}
