package com.myportfolio.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.myportfolio.backend.model.Role;
import com.myportfolio.backend.model.User;
import com.myportfolio.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor 
public class AdminInitializer implements CommandLineRunner {
    
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Value ("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {

        if (!userRepository.existsByUsername(adminUsername)) {

            User admin = User.builder()
                    .username(adminUsername)
                    .password(
                            passwordEncoder.encode(adminPassword)
                    )
                    .role(Role.ADMIN)
                    .enabled(true)
                    .build();

            userRepository.save(admin);

            System.out.println(
                    "Admin user created successfully."
            );
        }
    }
}
