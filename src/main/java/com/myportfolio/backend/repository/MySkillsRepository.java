package com.myportfolio.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myportfolio.backend.model.MySkills;

public interface MySkillsRepository extends JpaRepository<MySkills, Long> {
    
    List<MySkills> findByCategoryAndIsActiveTrue(String category);

}
