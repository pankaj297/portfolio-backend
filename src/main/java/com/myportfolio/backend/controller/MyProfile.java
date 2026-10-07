package com.myportfolio.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myportfolio.backend.dto.MyProfileRequestDto;
import com.myportfolio.backend.dto.MyProfileResponseDto;

import com.myportfolio.backend.services.MyProfileServices;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
@Tag(name = "Profile APIs", description = "Profile - Create , Update, Partial Update, Get and Delete ")
public class MyProfile {

    private final MyProfileServices myProfileServices;


    // ^ Get Profiles
    @GetMapping
    public ResponseEntity<List<MyProfileResponseDto>> getMyProfile() {
        // return
        // ResponseEntity.status(HttpStatus.OK).body(myProfileServices.getMyProfile());
        return ResponseEntity.ok(myProfileServices.getMyProfile());
    }

    // ^ Get Profile By Id
    @GetMapping("/{id}")
    public ResponseEntity<MyProfileResponseDto> getMyProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(myProfileServices.getMyProfileById(id));
    }

    // ^ Create Profile
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MyProfileResponseDto> createMyProfile(@Valid @ModelAttribute  MyProfileRequestDto myProfileRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(myProfileServices.createMyProfile(myProfileRequestDto));
    }

    // ^ Update Profile
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MyProfileResponseDto> updateMyProfile(@PathVariable Long id, @Valid
            @ModelAttribute  MyProfileRequestDto myProfileRequestDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(myProfileServices.updateMyProfile(id,
                myProfileRequestDto));
    }

    // ^ Delete Profile
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMyProfile(@PathVariable Long id) {
        myProfileServices.deleteMyProfileById(id);
        return ResponseEntity.noContent().build();
    }

        //^ Patch Update profile
    @PatchMapping("/{id}")
    public ResponseEntity<MyProfileResponseDto> updatePartialMyProfile(@PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(myProfileServices.updatePartialMyProfile(id, updates));
    }


    
}
