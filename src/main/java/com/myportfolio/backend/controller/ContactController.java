package com.myportfolio.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myportfolio.backend.dto.ContactRequestDto;
import com.myportfolio.backend.dto.ContactResponseDto;
import com.myportfolio.backend.services.ContactServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contact")
public class ContactController {
    
    private final ContactServices contactServices;

     // ^ Get Profiles
    @GetMapping("/")
    public ResponseEntity<List<ContactResponseDto>> getAllContact() {
        return ResponseEntity.status(HttpStatus.OK).body(contactServices.getAllContact());
    }
    
        // ^ Get Profile By Id
    @GetMapping("/{id}")
    public ResponseEntity<ContactResponseDto> getContactById(@PathVariable Long id) {
        return ResponseEntity.ok(contactServices.getContactById(id));
    }

        // ^ Create Profile
    @PostMapping("/")
    public ResponseEntity<ContactResponseDto> createContact(@Valid @RequestBody ContactRequestDto contactRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contactServices.createContact(contactRequestDto));
    }

        // ^ Update Profile
    @PutMapping("/{id}")
    public ResponseEntity<ContactResponseDto> updateContact(@PathVariable Long id, @Valid
    @RequestBody ContactRequestDto contactRequestDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(contactServices.updateContact(id,
                contactRequestDto));
    }
    
        // ^ Delete Profile
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactServices.deleteContactById(id);
        return ResponseEntity.noContent().build();
    }

}
