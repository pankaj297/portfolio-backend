package com.myportfolio.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myportfolio.backend.model.Achievements;

public interface AchievementsRepository extends JpaRepository<Achievements, Long> {

} 