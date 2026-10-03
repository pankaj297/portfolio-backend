package com.myportfolio.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myportfolio.backend.model.MyProfile;


public interface MyProfileRepository extends JpaRepository<MyProfile, Long> {
    boolean existsByEmail(String email);
    
}
