package com.myportfolio.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myportfolio.backend.dto.EducationRequestDto;
import com.myportfolio.backend.dto.EducationResponseDto;
import com.myportfolio.backend.services.EducationServices;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/education")
@Tag(name = "Education APIs", description = "Education - Create , Update, Partial Update, Get and Delete ")
public class EducationController {
    
    private final EducationServices educationServices;

    @GetMapping
    public ResponseEntity<List<EducationResponseDto>> getAllEducation() {
        return ResponseEntity.status(HttpStatus.OK).body(educationServices.getAllEducation());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EducationResponseDto> getEducationById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(educationServices.getEducationById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EducationResponseDto> createEducation(@Valid  @ModelAttribute EducationRequestDto educationRequestDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(educationServices.createEducation(educationRequestDto));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EducationResponseDto> updateEducation(@PathVariable Long id,
        @Valid  @ModelAttribute EducationRequestDto educationRequestDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(educationServices.getEducation(id, educationRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEducation(@PathVariable Long id) {
        educationServices.deleteEducationById(id);
        return ResponseEntity.noContent().build();
    }
}
