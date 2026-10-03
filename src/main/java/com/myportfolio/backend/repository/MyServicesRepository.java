package com.myportfolio.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myportfolio.backend.model.MyServices;

public interface MyServicesRepository extends JpaRepository<MyServices, Long> {
    
}
