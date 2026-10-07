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

import com.myportfolio.backend.dto.ResumeRequestDto;
import com.myportfolio.backend.dto.ResumeResponseDto;
import com.myportfolio.backend.services.ResumeServices;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resume")
@Tag(name = "Resume APIs", description = "Resume - Create , Update, Partial Update, Get and Delete ") 
public class ResumeController {

    private final ResumeServices resumeServices;

    @GetMapping
    public ResponseEntity<List<ResumeResponseDto>> getAllResumes() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(resumeServices.getAllResumes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResumeResponseDto> getResumeById(
            @PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(resumeServices.getResumeById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResumeResponseDto> createResume(
            @Valid @ModelAttribute  ResumeRequestDto resumeRequestDto) {

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(resumeServices.createResume(resumeRequestDto));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResumeResponseDto> updateResume(
            @PathVariable Long id,
            @Valid @ModelAttribute  ResumeRequestDto resumeRequestDto) {

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(resumeServices.updateResume(id, resumeRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResume(
            @PathVariable Long id) {

        resumeServices.deleteResumeById(id);
        return ResponseEntity.noContent().build();
    }
}