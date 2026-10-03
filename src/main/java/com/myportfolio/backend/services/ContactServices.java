package com.myportfolio.backend.services;

import java.util.List;


import com.myportfolio.backend.dto.ContactRequestDto;
import com.myportfolio.backend.dto.ContactResponseDto;


public interface ContactServices {

    List<ContactResponseDto> getAllContact();

    ContactResponseDto getContactById(Long id);

    ContactResponseDto createContact(ContactRequestDto contactRequestDto);

    ContactResponseDto updateContact(Long id, ContactRequestDto myProfileRequestDto);

    void deleteContactById(Long id);
    
}
