package com.myportfolio.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myportfolio.backend.model.Experience;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    
}
 