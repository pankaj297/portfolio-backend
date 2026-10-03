package com.myportfolio.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myportfolio.backend.model.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    
}
