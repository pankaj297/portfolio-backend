package com.myportfolio.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myportfolio.backend.dto.ExperienceRequestDto;
import com.myportfolio.backend.dto.ExperienceResponseDto;
import com.myportfolio.backend.services.ExperienceServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/experience")
public class ExperienceController {
    
    public final ExperienceServices experienceServices;

    //^ Get All Experience
    @GetMapping
    public ResponseEntity<List<ExperienceResponseDto>> getAllExperience() {
        return ResponseEntity.status(HttpStatus.OK).body(experienceServices.getAllExperience());
    }
    
    //^ Get Experience By id
    @GetMapping("/{id}")
    public ResponseEntity<ExperienceResponseDto> getExperienceById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(experienceServices.getExperienceById(id));
    }

    //^ Create Experience
    @PostMapping
    public ResponseEntity<ExperienceResponseDto> createExperience(
          @Valid  @ModelAttribute  ExperienceRequestDto experienceRequestDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(experienceServices.createExperience(experienceRequestDto));
    }

    //^ Update Experience
    @PutMapping("/{id}")
    public ResponseEntity<ExperienceResponseDto> updateExperience(@PathVariable Long id,
          @Valid  @ModelAttribute ExperienceRequestDto experienceRequestDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(experienceServices.updateExperience(id, experienceRequestDto));
    }

    // ^ Delete Experience
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExperience(@PathVariable Long id) {
        experienceServices.deleteExperienceById(id);
        return ResponseEntity.noContent().build();
    }

    //^ Update Partial Experience
    @PatchMapping("/{id}")
    public ResponseEntity<ExperienceResponseDto> updatePartialExperience(@PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(experienceServices.updatePartialExperience(id, updates));
    }

}
