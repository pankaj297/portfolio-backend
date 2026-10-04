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

import com.myportfolio.backend.dto.MySkillRequestDto;
import com.myportfolio.backend.dto.MySkillsResponseDto;
import com.myportfolio.backend.model.MySkills;
import com.myportfolio.backend.services.MySkillServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/skills")   
public class MySkillsController {
    
    private final MySkillServices mySkillServices;

    @GetMapping
    public ResponseEntity<List<MySkillsResponseDto>> getMyAllSkills() {
        return ResponseEntity.status(HttpStatus.OK).body(mySkillServices.getMyAllSkills());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MySkillsResponseDto> getMySkillById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(mySkillServices.getMySkillById(id));
    }

    @PostMapping
    public ResponseEntity<MySkillsResponseDto> createMySkills(@Valid @ModelAttribute  MySkillRequestDto mySkillRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mySkillServices.createMySkills(mySkillRequestDto));
    }


    @PutMapping("/{id}")
    public ResponseEntity<MySkillsResponseDto> updateMySkills(@PathVariable Long id,
           @Valid  @ModelAttribute  MySkillRequestDto mySkillRequestDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(mySkillServices.updateMySkills(id, mySkillRequestDto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMySkills(@PathVariable Long id) {
        mySkillServices.deleteMySkillsById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MySkillsResponseDto> updatePartialMySkills(@PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(mySkillServices.updatePartialMySkills(id, updates));
    }

    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<MySkills>> findByCategory(@PathVariable String category) {
        return ResponseEntity.status(HttpStatus.OK).body(mySkillServices.findByCategory(category));
    }
    

}
