package com.myportfolio.backend.servicesImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.myportfolio.backend.dto.ExperienceRequestDto;
import com.myportfolio.backend.dto.ExperienceResponseDto;
import com.myportfolio.backend.dto.MyServicesResponseDto;
import com.myportfolio.backend.exception.BadRequestException;
import com.myportfolio.backend.exception.FileUploadException;
import com.myportfolio.backend.exception.ResourceNotFoundException;
import com.myportfolio.backend.model.Experience;
import com.myportfolio.backend.model.MyProfile;
import com.myportfolio.backend.model.MyServices;
import com.myportfolio.backend.repository.ExperienceRepository;
import com.myportfolio.backend.repository.MyProfileRepository;
import com.myportfolio.backend.services.CloudinaryService;
import com.myportfolio.backend.services.ExperienceServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceServices {

    public final ExperienceRepository experienceRepository;
    public final ModelMapper modelMapper;
    public final CloudinaryService cloudinaryService;
   private final MyProfileRepository myProfileRepository;

    // ^ Get All Experience
    @Override
    public List<ExperienceResponseDto> getAllExperience() {
        List<Experience> experience = experienceRepository.findAll();
        return experience.stream().map(ex -> modelMapper.map(ex, ExperienceResponseDto.class)).toList();
    }

    // ^ Get Experience By Id
    @Override
    public ExperienceResponseDto getExperienceById(Long id) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience Id not found"));
        return modelMapper.map(experience, ExperienceResponseDto.class);
    }

    // ^ Create Experience
    @Override
    public ExperienceResponseDto createExperience(ExperienceRequestDto experienceRequestDto) {
        //! Experience experience = modelMapper.map(experienceRequestDto, Experience.class);
        Experience experience = new Experience();
        experience.setCompanyName(experienceRequestDto.getCompanyName());
        experience.setPosition(experienceRequestDto.getPosition());
        experience.setEmploymentType(experienceRequestDto.getEmploymentType());
        experience.setLocation(experienceRequestDto.getLocation());
        experience.setStartDate(experienceRequestDto.getStartDate());
        experience.setEndDate(experienceRequestDto.getEndDate());
        experience.setCurrentlyWorking(experienceRequestDto.getCurrentlyWorking());
        experience.setCompanyUrl(experienceRequestDto.getCompanyUrl());
        experience.setDescription(experienceRequestDto.getDescription());
        experience.setDisplayOrder(experienceRequestDto.getDisplayOrder());

        // & for Relationship code
        // ^ find profile
        MyProfile profile = myProfileRepository.findById(experienceRequestDto.getProfileId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ("Profile Not found with id : " + experienceRequestDto.getProfileId())));

        // ^ set profile
        experience.setProfile(profile);
        // & end Relationship code

        try{
            if(experienceRequestDto.getCompanyLogo() != null 
            && !experienceRequestDto.getCompanyLogo().isEmpty()){
                Map<String, Object> result = 
                cloudinaryService.uploadFile(experienceRequestDto.getCompanyLogo(), "image");
                experience.setCompanyLogo((String) result.get("secure_url"));
                experience.setCompanyLogoPublicId((String) result.get("public_id"));
            }
        } catch (IOException e) {
            throw new FileUploadException("Failed to upload company logo", e);
        }
        Experience saveExperience = experienceRepository.save(experience);
        return modelMapper.map(saveExperience, ExperienceResponseDto.class);
    }

    // ^ Update Experience
    @Override
    public ExperienceResponseDto updateExperience(Long id, ExperienceRequestDto experienceRequestDto) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience Id not found with id : " + id));
        
                //! modelMapper.map(experienceRequestDto, experience);
        experience.setCompanyName(experienceRequestDto.getCompanyName());
        experience.setPosition(experienceRequestDto.getPosition());
        experience.setEmploymentType(experienceRequestDto.getEmploymentType());
        experience.setLocation(experienceRequestDto.getLocation());
        experience.setStartDate(experienceRequestDto.getStartDate());
        experience.setEndDate(experienceRequestDto.getEndDate());
        experience.setCurrentlyWorking(experienceRequestDto.getCurrentlyWorking());
        experience.setCompanyUrl(experienceRequestDto.getCompanyUrl());
        experience.setDescription(experienceRequestDto.getDescription());
        experience.setDisplayOrder(experienceRequestDto.getDisplayOrder());

        // ? relationship code experience and profile
        // check is id is present or not and find profile
        if (experienceRequestDto.getProfileId() != null) {
            MyProfile profile = myProfileRepository.findById(experienceRequestDto.getProfileId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Profile not found with id : " + experienceRequestDto.getProfileId()));
            // set profile
            experience.setProfile(profile);
        }
        // ? relationship code experience and profile

        try {
            if(experienceRequestDto.getCompanyLogo() != null && !experienceRequestDto.getCompanyLogo().isEmpty()){

                if(experience.getCompanyLogoPublicId() != null && !experience.getCompanyLogo().isBlank()){
                    cloudinaryService.deleteFile(experience.getCompanyLogoPublicId(), "image");  
                }

                Map<String, Object> result = cloudinaryService.uploadFile(experienceRequestDto.getCompanyLogo(), "image");

                experience.setCompanyLogo((String) result.get("secure_url"));
                experience.setCompanyLogoPublicId((String) result.get("public_id"));
            }
        } catch (IOException e) {
            throw new FileUploadException("Failed to upload company logo", e);
        }
        
    
        Experience saveExperience = experienceRepository.save(experience);
        return modelMapper.map(saveExperience, ExperienceResponseDto.class);

    }

    // ^ Delete Experience
    @Override
    public void deleteExperienceById(Long id) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience Id not found with id : " + id));
                try{

                    if(experience.getCompanyLogoPublicId() != null && !experience.getCompanyLogoPublicId().isBlank()){
                        cloudinaryService.deleteFile(experience.getCompanyLogoPublicId(), "image");
                    }
                } catch (IOException e) {
                    throw new FileUploadException("Failed to delete Company Logo", e);
                }

                experienceRepository.delete(experience);
    }

    // ^ Update Partial Experience
    @Override
    public ExperienceResponseDto updatePartialExperience(
            Long id,
            Map<String, Object> updates) {

        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Experience Id not found with id : " + id));

        updates.forEach((field, value) -> {

            switch (field) {

                case "companyName":
                    experience.setCompanyName((String) value);
                    break;

                case "position":
                    experience.setPosition((String) value);
                    break;

                case "employmentType":
                    experience.setEmploymentType((String) value);
                    break;

                case "location":
                    experience.setLocation((String) value);
                    break;

                case "startDate":
                    experience.setStartDate((String) value);
                    break;

                case "endDate":
                    experience.setEndDate((String) value);
                    break;

                case "currentlyWorking":
                    experience.setCurrentlyWorking((Boolean) value);
                    break;

                case "companyUrl":
                    experience.setCompanyUrl((String) value);
                    break;

                case "description":
                    experience.setDescription((String) value);
                    break;

                case "displayOrder":
                    experience.setDisplayOrder((Integer) value);
                    break;

                default:
                    throw new BadRequestException(
                            "Invalid field: " + field);
            }
        });

        Experience updatedExperience = experienceRepository.save(experience);

        return modelMapper.map(
                updatedExperience,
                ExperienceResponseDto.class);
    }


    // ^ relation ship code my services and profile
    private MyServicesResponseDto mapToResponse(MyServices services) {

        MyServicesResponseDto response = modelMapper.map(services, MyServicesResponseDto.class);

        if (services.getProfile() != null) {
            response.setProfileId(services.getProfile().getId());
            response.setProfileName(services.getProfile().getFullName());
        }
        return response;
    }


    // ^ relation ship code my experience and profile
    private ExperienceResponseDto mapToResponse(Experience experience) {

        ExperienceResponseDto response = modelMapper.map(experience, ExperienceResponseDto.class);

        if (experience.getProfile() != null) {
            response.setProfileId(experience.getProfile().getId());
            response.setProfileName(experience.getProfile().getFullName());
        }
        return response;
    }


}
