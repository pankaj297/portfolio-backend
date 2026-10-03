package com.myportfolio.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myportfolio.backend.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long>{
    boolean existsByEmail(String email);

}
