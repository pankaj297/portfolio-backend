package com.myportfolio.backend.services;

import com.myportfolio.backend.dto.LoginRequestDto;
import com.myportfolio.backend.dto.LoginResponseDto;

public interface AuthService {

    LoginResponseDto login(LoginRequestDto request);
      
}
