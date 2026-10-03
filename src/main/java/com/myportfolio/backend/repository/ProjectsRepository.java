package com.myportfolio.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myportfolio.backend.model.Projects;

public interface ProjectsRepository extends JpaRepository<Projects, Long> {

} 