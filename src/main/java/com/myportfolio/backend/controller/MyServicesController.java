package com.myportfolio.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.myportfolio.backend.dto.MyServicesRequestDto;
import com.myportfolio.backend.dto.MyServicesResponseDto;
import com.myportfolio.backend.services.MyServicesServices;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/services")
@Tag(name = "Service APIs", description = "Service - Create , Update, Partial Update, Get and Delete ")
public class MyServicesController {
    
    private final MyServicesServices myServicesServices;

     @GetMapping
    public ResponseEntity<List<MyServicesResponseDto>> getAllServices() {
        return ResponseEntity.status(HttpStatus.OK).body(myServicesServices.getAllServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MyServicesResponseDto> getServicesById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(myServicesServices.getServicesById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MyServicesResponseDto> createServices(
            @Valid @RequestBody MyServicesRequestDto myServicesRequestDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(myServicesServices.createServices(
                myServicesRequestDto));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MyServicesResponseDto> updateServices(@PathVariable Long id,
            @Valid @RequestBody MyServicesRequestDto myServicesRequestDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(myServicesServices.updateServices(id,
                myServicesRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServices(@PathVariable Long id) {
        myServicesServices.deleteServicesById(id);
        return ResponseEntity.noContent().build();
    }

}
