package com.myportfolio.backend.servicesImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.myportfolio.backend.dto.AchievementsRequestDto;
import com.myportfolio.backend.dto.AchievementsResponseDto;

import com.myportfolio.backend.exception.FileUploadException;
import com.myportfolio.backend.exception.ResourceNotFoundException;
import com.myportfolio.backend.model.Achievements;
import com.myportfolio.backend.model.MyProfile;
import com.myportfolio.backend.repository.AchievementsRepository;
import com.myportfolio.backend.repository.MyProfileRepository;
import com.myportfolio.backend.services.AchievementsServices;
import com.myportfolio.backend.services.CloudinaryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AchievementsServicesImpl implements AchievementsServices {

    private final AchievementsRepository achievementsRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final MyProfileRepository myProfileRepository;

    @Override
    public List<AchievementsResponseDto> getAllAchievements() {
        List<Achievements> achievements = achievementsRepository.findAll();
        return achievements.stream().map(ac -> modelMapper.map(ac, AchievementsResponseDto.class)).toList();
    }

    @Override
    public AchievementsResponseDto getAchievementsById(Long id) {
        Achievements achievements = achievementsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Achievement Id not found"));
        return modelMapper.map(achievements, AchievementsResponseDto.class);
    }

    @Override
    public AchievementsResponseDto createAchievements(AchievementsRequestDto achievementsRequestDto) {
        //! Achievements achievements = modelMapper.map(achievementsRequestDto, Achievements.class);

        Achievements achievements = new Achievements();

        achievements.setTitle(achievementsRequestDto.getTitle());
        achievements.setDescription(achievementsRequestDto.getDescription());
        achievements.setDate(achievementsRequestDto.getDate());
        achievements.setOrganization(achievementsRequestDto.getOrganization());
        achievements.setDisplayOrder(achievementsRequestDto.getDisplayOrder());

        // & for Relationship code
        // ^ find profile
        MyProfile profile = myProfileRepository.findById(achievementsRequestDto.getProfileId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ("Profile Not found with id : " + achievementsRequestDto.getProfileId())));

        // ^ set profile
        achievements.setProfile(profile);
        // & end Relationship code

               try {
            if (achievementsRequestDto.getAchievementsImage() != null
                    && !achievementsRequestDto.getAchievementsImage().isEmpty()) {

                Map<String, Object> result = cloudinaryService.uploadFile(achievementsRequestDto.getAchievementsImage(),
                        "image");
                achievements.setAchievementsImage((String) result.get("secure_url"));
                achievements.setAchievementsImagePublicId((String) result.get("public_id"));
            }
        } catch (IOException e) {
            throw new FileUploadException("Failed to upload Achievements Image", e);
        }


        Achievements saveAchievements = achievementsRepository.save(achievements);
        return modelMapper.map(saveAchievements, AchievementsResponseDto.class);
    }
 

    @Override
    public AchievementsResponseDto updateAchievements(Long id, AchievementsRequestDto achievementsRequestDto) {
        Achievements achievements = achievementsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Achievement id not found with id : " + id));
        //! modelMapper.map(achievementsRequestDto, achievements);
        achievements.setTitle(achievementsRequestDto.getTitle());
        achievements.setDescription(achievementsRequestDto.getDescription());
        achievements.setDate(achievementsRequestDto.getDate());
        achievements.setOrganization(achievementsRequestDto.getOrganization());
        achievements.setDisplayOrder(achievementsRequestDto.getDisplayOrder());

        // ? relationship code achievements and profile
        // check is id is present or not and find profile
        if (achievementsRequestDto.getProfileId() != null) {
            MyProfile profile = myProfileRepository.findById(achievementsRequestDto.getProfileId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Profile not found with id : " + achievementsRequestDto.getProfileId()));
            // set profile
            achievements.setProfile(profile);
        }
        // ? relationship code achievements and profile

        try {
            if (achievementsRequestDto.getAchievementsImage() != null
                    && !achievementsRequestDto.getAchievementsImage().isEmpty()) {

                if (achievements.getAchievementsImagePublicId() != null
                        && !achievements.getAchievementsImagePublicId().isBlank()) {
                            cloudinaryService.deleteFile(achievements.getAchievementsImagePublicId(), "image");
                        }

                Map<String, Object> result = cloudinaryService.uploadFile(achievementsRequestDto.getAchievementsImage(),
                        "image");
                achievements.setAchievementsImage((String) result.get("secure_url"));
                achievements.setAchievementsImagePublicId((String) result.get("public_id"));
            }
        } catch (IOException e) {
            throw new FileUploadException("Failed to upload Achievements Image", e);
        }

        Achievements saveAchievements = achievementsRepository.save(achievements);
        return modelMapper.map(saveAchievements, AchievementsResponseDto.class);
    }

    @Override
    public void deleteAchievementsById(Long id) {
        Achievements achievements = achievementsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog id not found with id : " + id));

        try {
            if (achievements.getAchievementsImagePublicId() != null
                    && !achievements.getAchievementsImagePublicId().isBlank()) {
                cloudinaryService.deleteFile(achievements.getAchievementsImagePublicId(), "image");
            }
        } catch (IOException e) {
            throw new FileUploadException("Failed to delete Achievements Image", e);
        }
        achievementsRepository.delete(achievements);
    }
    
       // ^ relation ship code my achievements and profile
    private AchievementsResponseDto mapToResponse(Achievements achievements) {

        AchievementsResponseDto response = modelMapper.map(achievements, AchievementsResponseDto.class);

        if (achievements.getProfile() != null) {
            response.setProfileId(achievements.getProfile().getId());
            response.setProfileName(achievements.getProfile().getFullName());
        }
        return response;
    }

}
