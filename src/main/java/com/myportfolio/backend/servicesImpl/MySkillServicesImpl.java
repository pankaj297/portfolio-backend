package com.myportfolio.backend.servicesImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.myportfolio.backend.dto.MySkillRequestDto;
import com.myportfolio.backend.dto.MySkillsResponseDto;
import com.myportfolio.backend.exception.BadRequestException;
import com.myportfolio.backend.exception.FileUploadException;
import com.myportfolio.backend.exception.ResourceNotFoundException;
import com.myportfolio.backend.model.MyProfile;
import com.myportfolio.backend.model.MySkills;
import com.myportfolio.backend.repository.MyProfileRepository;
import com.myportfolio.backend.repository.MySkillsRepository;
import com.myportfolio.backend.services.CloudinaryService;
import com.myportfolio.backend.services.MySkillServices;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class MySkillServicesImpl implements MySkillServices {
    
    private final MySkillsRepository mySkillsRepository;
    private final ModelMapper modelMapper;

    private final CloudinaryService cloudinaryService;
    private final MyProfileRepository myProfileRepository;

    // ^ Get Profiles
    @Override
    public List<MySkillsResponseDto> getMyAllSkills() {
        List<MySkills> mySkills = mySkillsRepository.findAll();
        return mySkills.stream().map(skill -> modelMapper.map(skill, MySkillsResponseDto.class)).toList();
    }

    // ^ Get Profile By Id
    @Override
    public MySkillsResponseDto getMySkillById(Long id) {
        MySkills mySkills = mySkillsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill are not found Id with : " + id));
        //Entity to Dto
        return modelMapper.map(mySkills, MySkillsResponseDto.class);

    }

    // ^ Create Profile
    @Override
    public MySkillsResponseDto createMySkills(MySkillRequestDto mySkillRequestDto) {
        //! MySkills mySkills = modelMapper.map(mySkillRequestDto, MySkills.class);
        //Object create
        MySkills mySkills = new MySkills();

        // private String skill;
        mySkills.setSkill(mySkillRequestDto.getSkill());
        // private String category;
        mySkills.setCategory(mySkillRequestDto.getCategory());
        // private String level;
        mySkills.setLevel(mySkillRequestDto.getLevel());
        // private Integer yearsOfExperience;
        mySkills.setYearsOfExperience(mySkillRequestDto.getYearsOfExperience());
        // private Integer displayOrder;
        mySkills.setDisplayOrder(mySkillRequestDto.getDisplayOrder());
        // private Boolean isActive;
        mySkills.setIsActive(mySkillRequestDto.getIsActive());

        // & for Relationship code
        //^ find profile
        MyProfile profile = myProfileRepository.findById(mySkillRequestDto.getProfileId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ("Profile Not found with id : " + mySkillRequestDto.getProfileId())));

        //^ set profile
        mySkills.setProfile(profile);
        // & end Relationship code

        try {
            //& Upload profile image
            if (mySkillRequestDto.getIconImg() != null
                    && !mySkillRequestDto.getIconImg().isEmpty()) {
                Map<String, Object> result = cloudinaryService.uploadFile(
                        mySkillRequestDto.getIconImg(), "image");
                mySkills.setIconImg((String) result.get("secure_url"));
                // Cloudinary Public ID
                mySkills.setIconPublicId((String) result.get("public_id"));
            }
        } catch (IOException e) {
            throw new FileUploadException("File upload failed: " + e.getMessage());
        }
        MySkills saveSkills = mySkillsRepository.save(mySkills);
        return modelMapper.map(saveSkills, MySkillsResponseDto.class);
    }
    

    // ^ Update Profile
    @Override
    public MySkillsResponseDto updateMySkills(Long id, MySkillRequestDto mySkillRequestDto) {
        MySkills mySkills = mySkillsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill are not found with id : " + id));
        //! modelMapper.map(mySkillRequestDto, mySkills);
    
        // Update only normal fields
        mySkills.setSkill(mySkillRequestDto.getSkill());
        mySkills.setCategory(mySkillRequestDto.getCategory());
        mySkills.setLevel(mySkillRequestDto.getLevel());
        mySkills.setYearsOfExperience(mySkillRequestDto.getYearsOfExperience());
        mySkills.setDisplayOrder(mySkillRequestDto.getDisplayOrder());
        mySkills.setIsActive(mySkillRequestDto.getIsActive());

        // ? relationship code skills and profile
        // check is id is present or not and  find profile
        if (mySkillRequestDto.getProfileId() != null) {
            MyProfile profile = myProfileRepository.findById(mySkillRequestDto.getProfileId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Profile not found with id : " + mySkillRequestDto.getProfileId()));
            //set profile
            mySkills.setProfile(profile);
        }
         // ? relationship code skills and profile


          // & Upload profile image // 2. Update image ONLY if new image exists
            if (mySkillRequestDto.getIconImg() != null
                    && !mySkillRequestDto.getIconImg().isEmpty()) {

                try {
                    // Delete old Cloudinary image
                    if (mySkills.getIconPublicId() != null
                            && !mySkills.getIconPublicId().isBlank()) {
                                
                        cloudinaryService.deleteFile(
                        mySkills.getIconPublicId(),"image");
                    }

                    Map<String, Object> result = cloudinaryService.uploadFile(
                            mySkillRequestDto.getIconImg(), "image");
                    mySkills.setIconImg((String) result.get("secure_url"));
                    // Cloudinary Public ID
                    mySkills.setIconPublicId((String) result.get("public_id"));
                } catch (IOException e) {
                    throw new FileUploadException("File upload failed: " + e.getMessage());
                }
            }
        MySkills saveUpdateSkills = mySkillsRepository.save(mySkills);
        return modelMapper.map(saveUpdateSkills, MySkillsResponseDto.class);

    }

    // ^ Delete Profile
    @Override
    public void deleteMySkillsById(Long id) {
        // 1. Skill find karo
        MySkills mySkills = mySkillsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException( "Skill are not found with id " + id));

        try {
               // 2. Cloudinary image delete karo
            if (mySkills.getIconPublicId() != null
                    && !mySkills.getIconPublicId().isBlank()) {
                cloudinaryService.deleteFile( mySkills.getIconPublicId(), "image");
            }
        } catch (IOException e) {
            throw new FileUploadException("Failed to delete skill image from Cloudinary: "
                   + e.getMessage(),e);
        }

        // 3. Database se skill delete karo
        mySkillsRepository.delete(mySkills);

    }
  

    // ^ Patch Update profile
    @Override
    public MySkillsResponseDto updatePartialMySkills(Long id, Map<String, Object> updates) {
        MySkills mySkills = mySkillsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skills are not found with id : " + id));

        updates.forEach((fields, value) -> {
            switch (fields) {
                case "skill":
                    mySkills.setSkill((String) value);
                    break;

                case "category":
                    mySkills.setCategory((String) value);
                    break;

                case "level":
                    mySkills.setLevel((String) value);
                    break;

                case "yearsOfExperience":
                    mySkills.setYearsOfExperience((Integer) value);
                    break;

                case "displayOrder":
                    mySkills.setDisplayOrder((Integer) value);
                    break;

                case "isActive":
                    mySkills.setIsActive((Boolean) value);
                    break;

                default:
                    throw new BadRequestException("Field Is not supported" + fields);
            }
        });

        MySkills saveSkills = mySkillsRepository.save(mySkills);
        return modelMapper.map(saveSkills, MySkillsResponseDto.class);

    }
    

    //^ findByCategoryAndIsActiveTrue
    @Override
    public List<MySkills> findByCategory(String category) {
        return mySkillsRepository.findByCategoryAndIsActiveTrue(category);
    }


    //^ relation ship code skills and profile
    private MySkillsResponseDto mapToResponse(MySkills mySkills) {
        
        MySkillsResponseDto response = modelMapper.map(mySkills, MySkillsResponseDto.class);

        if (mySkills.getProfile() != null) {
            response.setProfileId(mySkills.getProfile().getId());
            response.setProfileName(mySkills.getProfile().getFullName());
        }
        return response;
    }

    
}
