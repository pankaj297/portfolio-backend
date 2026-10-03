package com.myportfolio.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myportfolio.backend.model.Education;

public interface EducationRepository extends JpaRepository<Education, Long> {
    
}
