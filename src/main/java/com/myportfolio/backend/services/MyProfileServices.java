package com.myportfolio.backend.services;

import java.util.List;
import java.util.Map;

import com.myportfolio.backend.dto.MyProfileRequestDto;
import com.myportfolio.backend.dto.MyProfileResponseDto;

public interface MyProfileServices {

    List<MyProfileResponseDto> getMyProfile();
    
    MyProfileResponseDto getMyProfileById(Long id);

    MyProfileResponseDto createMyProfile(MyProfileRequestDto myProfileRequestDto);

    MyProfileResponseDto updateMyProfile(Long id, MyProfileRequestDto myProfileRequestDto);

    void deleteMyProfileById(Long id);

    MyProfileResponseDto updatePartialMyProfile(Long id, Map<String,Object> updates);

    
} 