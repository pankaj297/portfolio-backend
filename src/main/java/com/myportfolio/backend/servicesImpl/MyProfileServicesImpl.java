package com.myportfolio.backend.servicesImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import com.myportfolio.backend.exception.FileUploadException;

import org.springframework.stereotype.Service;

import com.myportfolio.backend.dto.CertificationsResponseDto;
import com.myportfolio.backend.dto.MyProfileRequestDto;
import com.myportfolio.backend.dto.MyProfileResponseDto;
import com.myportfolio.backend.exception.BadRequestException;
import com.myportfolio.backend.exception.DuplicateResourceException;
import com.myportfolio.backend.exception.ResourceNotFoundException;
import com.myportfolio.backend.model.MyProfile;
import com.myportfolio.backend.repository.MyProfileRepository;
import com.myportfolio.backend.services.CloudinaryService;
import com.myportfolio.backend.services.MyProfileServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyProfileServicesImpl implements MyProfileServices {

    private final MyProfileRepository myProfileRepository;
    private final ModelMapper modelMapper;

    private final CloudinaryService cloudinaryService;

    // ^ Get Profiles
    @Override
    public List<MyProfileResponseDto> getMyProfile() {
        List<MyProfile> profiles = myProfileRepository.findAll();
        return profiles.stream().map(profile -> modelMapper.map(profile, MyProfileResponseDto.class)).toList();
    }

    // ^ Get Profile By Id
    @Override
    public MyProfileResponseDto getMyProfileById(Long id) {
        MyProfile myProfile = myProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found with id : " + id));
        return modelMapper.map(myProfile, MyProfileResponseDto.class);
    }

    // ^ Create Profile
    @Override
    public MyProfileResponseDto createMyProfile(MyProfileRequestDto myProfileRequestDto) {

        if (myProfileRepository.existsByEmail(myProfileRequestDto.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }
        MyProfile myProfile = modelMapper.map(myProfileRequestDto, MyProfile.class);

        try {
            //& Upload profile image
            if (myProfileRequestDto.getProfileImg() != null
                    && !myProfileRequestDto.getProfileImg().isEmpty()) {
                Map<String, Object> result = cloudinaryService.uploadFile(
                        myProfileRequestDto.getProfileImg(),  "image");
                myProfile.setProfileImg((String) result.get("secure_url"));
                // Cloudinary Public ID
                myProfile.setProfileImgPublicId((String) result.get("public_id"));
            }

            //& Upload resume
            if (myProfileRequestDto.getResume() != null
                    && !myProfileRequestDto.getResume().isEmpty()) {
                Map<String, Object> result = cloudinaryService.uploadFile(
                        myProfileRequestDto.getResume(), "raw");
                myProfile.setResumeUrl((String) result.get("secure_url"));
                // Cloudinary Public ID
                myProfile.setResumePublicId((String) result.get("public_id"));
            }
            
        } catch (IOException e) {
            throw new FileUploadException("File upload failed: " + e.getMessage());
        }

        MyProfile profile = myProfileRepository.save(myProfile);
        return modelMapper.map(profile, MyProfileResponseDto.class);

    }

    // ^ Update Profile
    @Override
    public MyProfileResponseDto updateMyProfile(Long id, MyProfileRequestDto myProfileRequestDto) {
        MyProfile myProfile = myProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile Not found with Id : " + id));
        // Email duplicate check
        if (myProfileRepository.existsByEmail(myProfileRequestDto.getEmail())
                && !myProfile.getEmail().equals(myProfileRequestDto.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        // update profile
        // modelMapper.map(myProfileRequestDto, myProfile);

       myProfile.setFullName(myProfileRequestDto.getFullName());
       myProfile.setHeadline(myProfileRequestDto.getHeadline());
       myProfile.setBio(myProfileRequestDto.getBio());
       myProfile.setLocation(myProfileRequestDto.getLocation());
       myProfile.setEmail(myProfileRequestDto.getEmail());
       myProfile.setPhone(myProfileRequestDto.getPhone());
       myProfile.setGithubUrl(myProfileRequestDto.getGithubUrl());
       myProfile.setLinkedinUrl(myProfileRequestDto.getLinkedinUrl());
       myProfile.setLeedcodeUrl(myProfileRequestDto.getLeedcodeUrl());
       myProfile.setAvailableForWork(myProfileRequestDto.isAvailableForWork());
       
       
            //& Update image ONLY if new image exists
           

            try {
                    
                 if (myProfileRequestDto.getProfileImg() != null
                         && !myProfileRequestDto.getProfileImg().isEmpty()) {

                     // Delete old image
                     if (myProfile.getProfileImgPublicId() != null
                             && !myProfile.getProfileImgPublicId().isBlank()) {
                         cloudinaryService.deleteFile(myProfile.getProfileImgPublicId(), "image");
                     }

                     // Upload new image
                     Map<String, Object> result = cloudinaryService.uploadFile(
                             myProfileRequestDto.getProfileImg(), "image");
                     // Set NEW image URL
                     myProfile.setProfileImg((String) result.get("secure_url"));
                     // Set NEW public ID
                     myProfile.setProfileImgPublicId((String) result.get("public_id"));
                 }

                    // & Update Resume ONLY if new resume is provided
                    if (myProfileRequestDto.getResume() != null
                            && !myProfileRequestDto.getResume().isEmpty()) {

                                // Delete old resume
                        if (myProfile.getResumePublicId() != null
                                && !myProfile.getResumePublicId().isBlank()) {

                            cloudinaryService.deleteFile(
                                    myProfile.getResumePublicId(),
                                    "raw");
                        }
                        // Upload new resume
                        Map<String, Object> result = cloudinaryService.uploadFile(
                                myProfileRequestDto.getResume(), "raw");
                        // Set new resume URL
                        myProfile.setResumeUrl((String) result.get("secure_url"));
                        // Set new public ID
                        myProfile.setResumePublicId((String) result.get("public_id"));
                    }

                } catch (IOException e) {
                    throw new FileUploadException(
                            "File upload failed: " + e.getMessage(), e);
                }
            

        // save profile
        MyProfile saveProfile = myProfileRepository.save(myProfile);
        return modelMapper.map(saveProfile, MyProfileResponseDto.class);
    }

    


    // ^ Delete Profile
    @Override
    public void deleteMyProfileById(Long id) {
        MyProfile myProfile = myProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile are not found with id : " + id));

    try{
        if (myProfile.getProfileImgPublicId() != null && !myProfile.getProfileImgPublicId().isBlank()) {
            cloudinaryService.deleteFile(myProfile.getProfileImgPublicId(), "image");
        }
        if (myProfile.getResumePublicId() != null && !myProfile.getResumePublicId().isBlank()) {
            cloudinaryService.deleteFile(myProfile.getResumePublicId(), "raw");
        }
    } catch (IOException e) {
        throw new FileUploadException("Failed to delete skill image from Cloudinary: "
                + e.getMessage(), e);
    }

    // 3. Database se skill delete karo
    myProfileRepository.delete(myProfile);
    
}






    // ^ Patch Update profile
    @Override
    public MyProfileResponseDto updatePartialMyProfile(Long id, Map<String, Object> updates) {
        MyProfile myProfile = myProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile Not found with Id : " + id));

        updates.forEach((field, value) -> {

            switch (field) {
                case "fullName":
                    myProfile.setFullName((String) value);
                    break;

                case "headline":
                    myProfile.setHeadline((String) value);
                    break;

                case "bio":
                    myProfile.setBio((String) value);
                    break;

                case "location":
                    myProfile.setLocation((String) value);
                    break;

                case "email":
                    String newEmail = (String) value;
                    if (myProfileRepository.existsByEmail(newEmail)
                            && !myProfile.getEmail().equals(newEmail)) {
                        throw new DuplicateResourceException("Email already exists");
                    }
                    myProfile.setEmail(newEmail);
                    break;

                case "phone":
                    myProfile.setPhone((String) value);
                    break;

                case "githubUrl":
                    myProfile.setGithubUrl((String) value);
                    break;

                case "linkedinUrl":
                    myProfile.setLinkedinUrl((String) value);
                    break;

                case "portfolioUrl":
                    myProfile.setPortfolioUrl((String) value);
                    break;

                case "leedcodeUrl":
                    myProfile.setLeedcodeUrl((String) value);
                    break;

                case "availableForWork":
                    myProfile.setAvailableForWork((Boolean) value);
                    break;

                default:
                    throw new BadRequestException(
                            "Field is not supported: " + field);
            }
        });

        MyProfile saveProfile = myProfileRepository.save(myProfile);
        return modelMapper.map(saveProfile, MyProfileResponseDto.class);
    }




    private MyProfileResponseDto mapToResponse(
            MyProfile profile) {

        MyProfileResponseDto response = modelMapper.map(
                profile,
                MyProfileResponseDto.class);

        if (profile.getCertifications() != null) {

            List<CertificationsResponseDto> certifications = profile.getCertifications()
                    .stream()
                    .map(certification -> {

                        CertificationsResponseDto dto = modelMapper.map(
                                certification,
                                CertificationsResponseDto.class);

                        dto.setProfileId(
                                profile.getId());

                        return dto;
                    })
                    .toList();

            response.setCertifications(certifications);
        }

        return response;
    }

    

}
