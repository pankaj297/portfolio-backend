package com.myportfolio.backend.servicesImpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.myportfolio.backend.dto.ResumeRequestDto;
import com.myportfolio.backend.dto.ResumeResponseDto;
import com.myportfolio.backend.exception.FileUploadException;
import com.myportfolio.backend.exception.ResourceNotFoundException;
import com.myportfolio.backend.model.Resume;
import com.myportfolio.backend.repository.ResumeRepository;
import com.myportfolio.backend.services.CloudinaryService;
import com.myportfolio.backend.services.ResumeServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResumeServicesImpl implements ResumeServices {

    private final ResumeRepository resumeRepository;
    private final ModelMapper modelMapper;
        private final CloudinaryService cloudinaryService;

    @Override
    public List<ResumeResponseDto> getAllResumes() {
        List<Resume> resumes = resumeRepository.findAll();
        return resumes.stream().map(resume -> modelMapper.map(resume, ResumeResponseDto.class)) .toList();
    }

    @Override
    public ResumeResponseDto getResumeById(Long id) {

        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resume id not found with id : " + id));
        return modelMapper.map(resume, ResumeResponseDto.class);
    }

    //^ Create Resume
    @Override
    public ResumeResponseDto createResume(ResumeRequestDto resumeRequestDto) {
        Resume resume = modelMapper.map(resumeRequestDto, Resume.class);

        try {
            if (resumeRequestDto.getResumeFileUrl() != null && !resumeRequestDto.getResumeFileUrl().isEmpty()) {
                Map<String, Object> result = cloudinaryService.uploadFile(resumeRequestDto.getResumeFileUrl(), "raw");
                resume.setResumeFileUrl((String) result.get("secure_url"));
                resume.setResumePublicId((String) result.get("public_id"));
            }
        } catch (IOException e) {
            throw new FileUploadException("File upload failed: " + e.getMessage());
        }

        // Automatically set upload time
        resume.setUploadedAt(LocalDateTime.now());
        Resume savedResume = resumeRepository.save(resume);
        return modelMapper.map(savedResume, ResumeResponseDto.class);
    }

    //^ Update Resume
    @Override
    public ResumeResponseDto updateResume(Long id, ResumeRequestDto resumeRequestDto) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resume id not found with id : " + id));

        // modelMapper.map(resumeRequestDto, resume);

        resume.setTitle(resumeRequestDto.getTitle());
        resume.setVersion(resumeRequestDto.getVersion());
        resume.setIsPrimary(resumeRequestDto.getIsPrimary());

        if (resumeRequestDto.getResumeFileUrl() != null && !resumeRequestDto.getResumeFileUrl().isEmpty()) {

            try {
                if (resume.getResumePublicId() != null
                        && !resume.getResumePublicId().isBlank()) {

                    cloudinaryService.deleteFile(
                            resume.getResumePublicId(), "raw");
                }

                Map<String, Object> result = cloudinaryService.uploadFile(resumeRequestDto.getResumeFileUrl(), "raw");

                resume.setResumeFileUrl((String) result.get("secure_url"));
                resume.setResumePublicId((String) result.get("public_id"));
            } catch (IOException e) {
                throw new FileUploadException("File upload failed: " + e.getMessage());
            }
        }
        Resume savedResume = resumeRepository.save(resume);
        return modelMapper.map(savedResume,ResumeResponseDto.class);
    }

    @Override
    public void deleteResumeById(Long id) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill are not found with id " + id));

        try {
            if (resume.getResumePublicId() != null
                    && !resume.getResumePublicId().isBlank()) {
                cloudinaryService.deleteFile(resume.getResumePublicId(), "raw");
            }
        } catch (IOException e) {
            throw new FileUploadException("Failed to delete skill image from Cloudinary: "
                    + e.getMessage(), e);
        }

        //  Database se resume delete
        resumeRepository.delete(resume);
    }

}