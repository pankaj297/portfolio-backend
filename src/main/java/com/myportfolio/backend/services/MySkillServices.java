package com.myportfolio.backend.services;


import java.util.List;
import java.util.Map;


import com.myportfolio.backend.dto.MySkillRequestDto;
import com.myportfolio.backend.dto.MySkillsResponseDto;

public interface MySkillServices {

    List<MySkillsResponseDto> getMyAllSkills();

    MySkillsResponseDto getMySkillById(Long id);
  
    MySkillsResponseDto createMySkills(MySkillRequestDto mySkillRequestDto);

    MySkillsResponseDto updateMySkills(Long id, MySkillRequestDto mySkillRequestDto);
    
    void deleteMySkillsById(Long id);

    MySkillsResponseDto updatePartialMySkills(Long id, Map<String,Object> updates);

    
}
