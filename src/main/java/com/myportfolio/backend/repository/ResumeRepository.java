package com.myportfolio.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myportfolio.backend.model.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    
}
