package com.myportfolio.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myportfolio.backend.model.MySkills;

public interface MySkillsRepository extends JpaRepository<MySkills, Long> {
    
}
