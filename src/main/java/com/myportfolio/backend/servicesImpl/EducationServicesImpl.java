package com.myportfolio.backend.servicesImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.myportfolio.backend.dto.EducationRequestDto;
import com.myportfolio.backend.dto.EducationResponseDto;
import com.myportfolio.backend.exception.FileUploadException;
import com.myportfolio.backend.exception.ResourceNotFoundException;
import com.myportfolio.backend.model.Education;
import com.myportfolio.backend.model.MyProfile;
import com.myportfolio.backend.repository.EducationRepository;
import com.myportfolio.backend.repository.MyProfileRepository;
import com.myportfolio.backend.services.CloudinaryService;
import com.myportfolio.backend.services.EducationServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EducationServicesImpl implements EducationServices {

    private final EducationRepository educationRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final MyProfileRepository myProfileRepository;

    // ^ Get All Education
    @Override
    public List<EducationResponseDto> getAllEducation() {
        List<Education> educations = educationRepository.findAll();
        return educations.stream().map(ex -> modelMapper.map(ex, EducationResponseDto.class)).toList();
    }

    // ^ Get Education By Id
    @Override
    public EducationResponseDto getEducationById(Long id) {
        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Education Id not found"));
        return modelMapper.map(education, EducationResponseDto.class);
    }

    // ^ Create Education
    @Override
    public EducationResponseDto createEducation(EducationRequestDto educationRequestDto) {
        // ! Education education = modelMapper.map(educationRequestDto, Education.class);
        // Create NEW entity manually
        // ID will remain null, so database will generate new ID
        Education education = new Education();

        education.setDegree(educationRequestDto.getDegree());
        education.setFieldOfStudy(educationRequestDto.getFieldOfStudy());
        education.setInstitution(educationRequestDto.getInstitution());
        education.setLocation(educationRequestDto.getLocation());
        education.setStartDate(educationRequestDto.getStartDate());
        education.setEndDate(educationRequestDto.getEndDate());
        education.setCurrentlyStudying(educationRequestDto.getCurrentlyStudying());
        education.setGrade(educationRequestDto.getGrade());
        education.setDescription(educationRequestDto.getDescription());
        education.setInstitutionUrl(educationRequestDto.getInstitutionUrl());

        // & for Relationship code
        //* find profile

        MyProfile profile = myProfileRepository.findById(educationRequestDto.getProfileId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "profile Not found with id : " + educationRequestDto.getProfileId()));

        //* set profile
        education.setProfile(profile);
        // & End Relationship code


        try {
            if (educationRequestDto.getInstitutionLogo() != null
                    && !educationRequestDto.getInstitutionLogo().isEmpty()) {
                Map<String, Object> result = cloudinaryService.uploadFile(educationRequestDto.getInstitutionLogo(),
                        "image");
                education.setInstitutionLogo((String) result.get("secure_url"));
                education.setInstitutionLogoPublicId((String) result.get("public_id"));
            }
        } catch (IOException e) {
            throw new FileUploadException("Failed to upload institution logo ", e);
        }

        Education saveEducation = educationRepository.save(education);
        return modelMapper.map(saveEducation, EducationResponseDto.class);
    }

    // ^ Update Education
    @Override
    public EducationResponseDto getEducation(Long id, EducationRequestDto educationRequestDto) {
        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Education id not found with id : " + id));

        //! modelMapper.map(educationRequestDto, education);
        education.setDegree(educationRequestDto.getDegree());
        education.setFieldOfStudy(educationRequestDto.getFieldOfStudy());
        education.setInstitution(educationRequestDto.getInstitution());
        education.setLocation(educationRequestDto.getLocation());
        education.setStartDate(educationRequestDto.getStartDate());
        education.setEndDate(educationRequestDto.getEndDate());
        education.setCurrentlyStudying(educationRequestDto.getCurrentlyStudying());
        education.setGrade(educationRequestDto.getGrade());
        education.setDescription(educationRequestDto.getDescription());
        education.setInstitutionUrl(educationRequestDto.getInstitutionUrl());


        // ? relationship code certification and profile
        if (educationRequestDto.getProfileId() != null) {
            MyProfile profile = myProfileRepository.findById(educationRequestDto.getProfileId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Profile not found with di : " + educationRequestDto.getProfileId()));
            //set profile
            education.setProfile(profile);
         }
        // ? relationship code certification and profile

        try {
            if (educationRequestDto.getInstitutionLogo() != null
                    && !educationRequestDto.getInstitutionLogo().isEmpty()) {

                if (education.getInstitutionLogoPublicId() != null
                        && !education.getInstitutionLogoPublicId().isBlank()) {
                    cloudinaryService.deleteFile(education.getInstitutionLogoPublicId(), "image");
                }

                Map<String, Object> result = cloudinaryService.uploadFile(educationRequestDto.getInstitutionLogo(),
                        "image");
                education.setInstitutionLogo((String) result.get("secure_url"));
                education.setInstitutionLogoPublicId((String) result.get("public_id"));
            }
        } catch (IOException e) {
            throw new FileUploadException("Failed to upload institution logo", e);
        }

        Education saveEducation = educationRepository.save(education);
        return modelMapper.map(saveEducation, EducationResponseDto.class);
    }

    // ^ Delete Education
    @Override
    public void deleteEducationById(Long id) {
        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Education Id not found with id : " + id));

        try {
            if (education.getInstitutionLogoPublicId() != null && !education.getInstitutionLogoPublicId().isBlank()) {
                cloudinaryService.deleteFile(education.getInstitutionLogoPublicId(), "image");
            }
        } catch (IOException e) {
            throw new FileUploadException("Failed to delete institution logo", e);
        }

        educationRepository.delete(education);

    }



    // ? relation ship code certification and
    private EducationResponseDto mapToResponse(Education education) {
        EducationResponseDto response = modelMapper.map(education, EducationResponseDto.class);

        if (education.getProfile() != null) {
            response.setProfileId(education.getProfile().getId());
            response.setProfileName(education.getProfile().getFullName());
        }
        return response;
    }



}
