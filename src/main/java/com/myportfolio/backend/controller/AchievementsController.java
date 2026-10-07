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

import com.myportfolio.backend.dto.AchievementsRequestDto;
import com.myportfolio.backend.dto.AchievementsResponseDto;
import com.myportfolio.backend.services.AchievementsServices;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/achievements")
@Tag(name = "Achievements APIs", description = "Achievements - Create , Update, Partial Update, Get and Delete ")
public class AchievementsController {

    private final AchievementsServices achievementsServices;

    @GetMapping
    public ResponseEntity<List<AchievementsResponseDto>> getAllAchievements() {
        return ResponseEntity.status(HttpStatus.OK).body(achievementsServices.getAllAchievements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AchievementsResponseDto> getAchievementsById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(achievementsServices.getAchievementsById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AchievementsResponseDto> createAchievements(
            @Valid @ModelAttribute  AchievementsRequestDto achievementsRequestDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(achievementsServices.createAchievements(
                achievementsRequestDto));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AchievementsResponseDto> updateAchievement(@PathVariable Long id,
            @Valid @ModelAttribute AchievementsRequestDto achievementsRequestDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(achievementsServices.updateAchievements(id,
                achievementsRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAchievements(@PathVariable Long id) {
        achievementsServices.deleteAchievementsById(id);
        return ResponseEntity.noContent().build();
    }

}
