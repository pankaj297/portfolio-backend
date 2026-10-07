package com.myportfolio.backend.servicesImpl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.myportfolio.backend.dto.LoginRequestDto;
import com.myportfolio.backend.dto.LoginResponseDto;
import com.myportfolio.backend.security.JwtService;
import com.myportfolio.backend.services.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class AuthServiceImpl implements AuthService {
    
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );
                
       UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        String token =
                jwtService.generateToken(userDetails);

        String role =
                userDetails.getAuthorities()
                        .iterator()
                        .next()
                        .getAuthority()
                        .replace("ROLE_", "");

        return LoginResponseDto.builder()
                .token(token)
                .tokenType("Bearer")
                .username(userDetails.getUsername())
                .role(role)
                .build();
    }
    
}
