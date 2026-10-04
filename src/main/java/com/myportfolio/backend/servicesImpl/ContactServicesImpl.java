package com.myportfolio.backend.servicesImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.myportfolio.backend.dto.ContactRequestDto;
import com.myportfolio.backend.dto.ContactResponseDto;
import com.myportfolio.backend.exception.DuplicateResourceException;
import com.myportfolio.backend.exception.ResourceNotFoundException;
import com.myportfolio.backend.model.Contact;
import com.myportfolio.backend.repository.ContactRepository;
import com.myportfolio.backend.services.ContactServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class ContactServicesImpl implements ContactServices {
    
    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<ContactResponseDto> getAllContact() {
            List<Contact> contact = contactRepository.findAll();
        return contact.stream().map(con -> modelMapper.map(con, ContactResponseDto.class)).toList();
    }

    @Override
    public ContactResponseDto getContactById(Long id) {
               Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id : "+ id));
        return modelMapper.map(contact, ContactResponseDto.class);
    }

    @Override
    public ContactResponseDto createContact(ContactRequestDto contactRequestDto) {
        if (contactRepository.existsByEmail(contactRequestDto.getEmail())) {
            throw new DuplicateResourceException("Contact already exists");
        }
        Contact myContact = modelMapper.map(contactRequestDto, Contact.class);
        Contact contact = contactRepository.save(myContact);
        return modelMapper.map(contact, ContactResponseDto.class);
    }

    @Override
    public ContactResponseDto updateContact(Long id, ContactRequestDto contactRequestDto) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact Not found with Id : " + id));
                
        if (contactRepository.existsByEmail(contactRequestDto.getEmail())
                && !contact.getEmail().equals(contactRequestDto.getEmail())) {
            throw new DuplicateResourceException("Contact already exists");
        }
        // update profile
        modelMapper.map(contactRequestDto, contact);
        // save profile
        Contact saveContact = contactRepository.save(contact);
        return modelMapper.map(saveContact, ContactResponseDto.class);
    }

    @Override
    public void deleteContactById(Long id) {
        if (!contactRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contact Not found with id : " + id);
        }
        contactRepository.deleteById(id);
        
    }


    
}
