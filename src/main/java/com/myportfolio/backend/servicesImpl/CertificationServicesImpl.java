package com.myportfolio.backend.servicesImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.myportfolio.backend.dto.CertificationsRequestDto;
import com.myportfolio.backend.dto.CertificationsResponseDto;
import com.myportfolio.backend.exception.FileUploadException;
import com.myportfolio.backend.exception.ResourceNotFoundException;
import com.myportfolio.backend.model.Certifications;
import com.myportfolio.backend.model.MyProfile;
import com.myportfolio.backend.repository.CertificationsRepository;
import com.myportfolio.backend.repository.MyProfileRepository;
import com.myportfolio.backend.services.CertificationServices;
import com.myportfolio.backend.services.CloudinaryService;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class CertificationServicesImpl implements CertificationServices {
    
    private final CertificationsRepository certificationsRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final MyProfileRepository myProfileRepository;

    @Override
    public List<CertificationsResponseDto> getAllCertification() {
        List<Certifications> certifications = certificationsRepository.findAll();
        // return certifications.stream().map(ce -> modelMapper.map(ce, CertificationsResponseDto.class)).toList();
        return certifications.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CertificationsResponseDto getCertificationById(Long id) {
        Certifications certifications = certificationsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certifications Id not found"));
        // return modelMapper.map(certifications, CertificationsResponseDto.class);
        return mapToResponse(certifications);
    }

    // ^ Create Certification
    @Override
    public CertificationsResponseDto createCertification(CertificationsRequestDto certificationsRequestDto) {
        // Certifications certifications = modelMapper.map(certificationsRequestDto, Certifications.class);

        // Create NEW entity manually
        // ID will remain null, so database will generate new ID
        Certifications certifications = new Certifications();

        certifications.setName(certificationsRequestDto.getName());
        certifications.setOrganization(certificationsRequestDto.getOrganization());
        certifications.setIssueDate(certificationsRequestDto.getIssueDate());
        certifications.setCredentialCode(certificationsRequestDto.getCredentialCode());
        certifications.setCredentialUrl(certificationsRequestDto.getCredentialUrl());
        certifications.setDescription(certificationsRequestDto.getDescription());
        certifications.setSkills(certificationsRequestDto.getSkills());

        //* */ for Relationship code
        // Find Profile
        MyProfile profile = myProfileRepository.findById( certificationsRequestDto.getProfileId()).orElseThrow(() -> new ResourceNotFoundException(
                "Profile not found with id : " + certificationsRequestDto.getProfileId()));
                                
            // Set Profile
            certifications.setProfile(profile);
            // * */  End Relationship code

        try {
            if (certificationsRequestDto.getCertificateImage() != null
                    && !certificationsRequestDto.getCertificateImage().isEmpty()) {

                Map<String, Object> result = cloudinaryService
                        .uploadFile(certificationsRequestDto.getCertificateImage(), "image");
                certifications.setCertificateImage((String) result.get("secure_url"));
                certifications.setCertificateImagePublicId((String) result.get("public_id"));
            }
        } catch (IOException e) {
            throw new FileUploadException("Failed to upload certificate Image", e);
        }
        Certifications saveCertifications = certificationsRepository.save(certifications);
        // return modelMapper.map(saveCertifications, CertificationsResponseDto.class);
        return mapToResponse(saveCertifications);
    }








    //^ Update Certification
    @Override
    public CertificationsResponseDto updateCertification(Long id, CertificationsRequestDto certificationsRequestDto) {
        Certifications certifications = certificationsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certifications id not found with id : " + id));
        // modelMapper.map(certificationsRequestDto, certifications);

        certifications.setName(certificationsRequestDto.getName());
        certifications.setOrganization(certificationsRequestDto.getOrganization());
        certifications.setIssueDate(certificationsRequestDto.getIssueDate());
        certifications.setCredentialCode(certificationsRequestDto.getCredentialCode());
        certifications.setCredentialUrl(certificationsRequestDto.getCredentialUrl());
        certifications.setDescription(certificationsRequestDto.getDescription());
        certifications.setSkills(certificationsRequestDto.getSkills());

        // Update Profile
        if (certificationsRequestDto.getProfileId() != null) {

            MyProfile profile = myProfileRepository.findById(certificationsRequestDto.getProfileId()).orElseThrow(
                    () -> new ResourceNotFoundException("Profile not found with id : "
                            + certificationsRequestDto.getProfileId()));

            certifications.setProfile(profile);
        }

        try {
            if (certificationsRequestDto.getCertificateImage() != null
                    && !certificationsRequestDto.getCertificateImage().isEmpty()) {

                if (certifications.getCertificateImagePublicId() != null
                        && !certifications.getCertificateImagePublicId().isBlank()) {
                    cloudinaryService.deleteFile(certifications.getCertificateImagePublicId(), "image");
                }
                Map<String, Object> result = cloudinaryService
                        .uploadFile(certificationsRequestDto.getCertificateImage(), "image");
                certifications.setCertificateImage((String) result.get("secure_url"));
                certifications.setCertificateImagePublicId((String) result.get("public_id"));
            }
        } catch (IOException e) {
            throw new FileUploadException("Failed to upload certificate Image", e);
        }
        Certifications saveCertifications = certificationsRepository.save(certifications);
        // return modelMapper.map(saveCertifications, CertificationsResponseDto.class);
        return mapToResponse(saveCertifications);
    }
    
    

    @Override
    public void deleteCertificationById(Long id) {
       Certifications certifications = certificationsRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Certifications id not found with id : " + id));

        try{
            if(certifications.getCertificateImagePublicId() != null && !certifications.getCertificateImagePublicId().isBlank()){
                cloudinaryService.deleteFile(certifications.getCertificateImagePublicId(), "image");
            }
        }catch (IOException e) {
            throw new FileUploadException("Failed to delete certificate Image", e);
        }
        certificationsRepository.delete(certifications);
    }




    private CertificationsResponseDto mapToResponse(
            Certifications certifications) {

        CertificationsResponseDto response = modelMapper.map(
                certifications,
                CertificationsResponseDto.class);

        if (certifications.getProfile() != null) {

            response.setProfileId(
                    certifications.getProfile().getId());
            response.setProfileName(
                    certifications.getProfile().getFullName());
        }

        return response;
    }

    
}
