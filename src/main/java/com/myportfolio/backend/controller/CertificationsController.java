package com.myportfolio.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myportfolio.backend.dto.CertificationsRequestDto;
import com.myportfolio.backend.dto.CertificationsResponseDto;
import com.myportfolio.backend.services.CertificationServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/certification") 
public class CertificationsController {
    
    private final CertificationServices certificationServices;

        @GetMapping
    public ResponseEntity<List<CertificationsResponseDto>> getAllCertification() {
        return ResponseEntity.status(HttpStatus.OK).body(certificationServices.getAllCertification());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificationsResponseDto> getCertificationById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(certificationServices.getCertificationById(id));
    }

    @PostMapping
    public ResponseEntity<CertificationsResponseDto> createCertification(@Valid  @ModelAttribute  CertificationsRequestDto certificationsRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(certificationServices.createCertification(
                certificationsRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificationsResponseDto> updateCertification(@PathVariable Long id,
        @Valid  @ModelAttribute CertificationsRequestDto certificationsRequestDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(certificationServices.updateCertification(id, 
                certificationsRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertification(@PathVariable Long id) {
        certificationServices.deleteCertificationById(id);
        return ResponseEntity.noContent().build();
    }

    
}
