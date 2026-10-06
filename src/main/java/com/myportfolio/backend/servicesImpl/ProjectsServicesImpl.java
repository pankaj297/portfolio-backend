package com.myportfolio.backend.servicesImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import com.myportfolio.backend.dto.ProjectsRequestDto;
import com.myportfolio.backend.dto.ProjectsResponseDto;
import com.myportfolio.backend.exception.BadRequestException;
import com.myportfolio.backend.exception.FileUploadException;
import com.myportfolio.backend.exception.ResourceNotFoundException;
import com.myportfolio.backend.model.MyProfile;
import com.myportfolio.backend.model.Projects;
import com.myportfolio.backend.repository.MyProfileRepository;
import com.myportfolio.backend.repository.ProjectsRepository;
import com.myportfolio.backend.services.CloudinaryService;
import com.myportfolio.backend.services.ProjectsServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectsServicesImpl implements ProjectsServices {

    public final ProjectsRepository projectsRepository;
    public final ModelMapper modelMapper;

    public final CloudinaryService cloudinaryService;
     private final MyProfileRepository myProfileRepository;

    // ^ Get All Projects
    @Override
    public List<ProjectsResponseDto> getAllProjects() {
        List<Projects> projects = projectsRepository.findAll();
        return projects.stream().map(pro -> modelMapper.map(pro, ProjectsResponseDto.class)).toList();
    }

    // ^ Get Project with id
    @Override
    public ProjectsResponseDto getProjectById(Long id) {
        Projects projects = projectsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id : " + id));
        return modelMapper.map(projects, ProjectsResponseDto.class);
    }

    // ^ Create Projects
    @Override
    public ProjectsResponseDto createProjects(ProjectsRequestDto projectsRequestDto) {
        //! Projects projects = modelMapper.map(projectsRequestDto, Projects.class);

        Projects projects = new Projects();

        projects.setTitle(projectsRequestDto.getTitle());
        projects.setShortDescription(projectsRequestDto.getShortDescription());
        projects.setDescription(projectsRequestDto.getDescription());
        projects.setGithubUrl(projectsRequestDto.getGithubUrl());
        projects.setLiveUrl(projectsRequestDto.getLiveUrl());
        projects.setStartDate(projectsRequestDto.getStartDate());
        projects.setEndDate(projectsRequestDto.getEndDate());
        projects.setStatus(projectsRequestDto.getStatus());
        projects.setFeatured(projectsRequestDto.getFeatured());
        projects.setDisplayOrder(projectsRequestDto.getDisplayOrder());

           // & for Relationship code
        // ^ find profile
        MyProfile profile = myProfileRepository.findById(projectsRequestDto.getProfileId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ("Profile Not found with id : " + projectsRequestDto.getProfileId())));

        // ^ set profile
        projects.setProfile(profile);
        // & end Relationship code
        
        try{
            if(projectsRequestDto.getThumbnailImg() != null && !projectsRequestDto.getThumbnailImg().isEmpty()){
                Map<String, Object> result = cloudinaryService.uploadFile(projectsRequestDto.getThumbnailImg(), "image");

                projects.setThumbnailImg((String) result.get("secure_url"));
                projects.setThumbnailPublicId((String) result.get("public_id"));
            }
        }catch (IOException e) {
            throw new FileUploadException("File upload failed: " + e.getMessage());
        }
        
        Projects saveProjects = projectsRepository.save(projects);
        return modelMapper.map(saveProjects, ProjectsResponseDto.class);

    }

    // ^ Update Projects
    @Override
    public ProjectsResponseDto updateProjects(Long id, ProjectsRequestDto projectsRequestDto) {
        Projects projects = projectsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id : " + id));


        //! modelMapper.map(projectsRequestDto, projects);
        projects.setTitle(projectsRequestDto.getTitle());
        projects.setShortDescription(projectsRequestDto.getShortDescription());
        projects.setDescription(projectsRequestDto.getDescription());
        projects.setGithubUrl(projectsRequestDto.getGithubUrl());
        projects.setLiveUrl(projectsRequestDto.getLiveUrl());
        projects.setStartDate(projectsRequestDto.getStartDate());
        projects.setEndDate(projectsRequestDto.getEndDate());
        projects.setStatus(projectsRequestDto.getStatus());
        projects.setFeatured(projectsRequestDto.getFeatured());
        projects.setDisplayOrder(projectsRequestDto.getDisplayOrder());

        // ? relationship code projects and profile
        // check is id is present or not and find profile
        if (projectsRequestDto.getProfileId() != null) {
            MyProfile profile = myProfileRepository.findById(projectsRequestDto.getProfileId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Profile not found with id : " + projectsRequestDto.getProfileId()));
            // set profile
            projects.setProfile(profile);
        }
        // ? relationship code projects and profile

        if (projectsRequestDto.getThumbnailImg() != null && !projectsRequestDto.getThumbnailImg().isEmpty()) {
            
            try{
                if(projects.getThumbnailPublicId() != null && !projects.getThumbnailPublicId().isBlank()){
                    cloudinaryService.deleteFile(projects.getThumbnailPublicId(), "image");
                    
                }

                Map<String, Object> result = cloudinaryService.uploadFile(projectsRequestDto.getThumbnailImg(),
                        "image");

                projects.setThumbnailImg((String) result.get("secure_url"));
                projects.setThumbnailPublicId((String) result.get("public_id"));


            } catch (IOException e) {
                throw new FileUploadException("File upload failed: " + e.getMessage());
            }

        }

        Projects saveProjects = projectsRepository.save(projects);
        return modelMapper.map(saveProjects, ProjectsResponseDto.class);

    }

    // ^ Delete Projects
    @Override
    public void deleteProjectById(Long id) {
          Projects projects = projectsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("projects are not found with id " + id));

        try {
            if (projects.getThumbnailPublicId() != null
                    && !projects.getThumbnailPublicId().isBlank()) {
                cloudinaryService.deleteFile(projects.getThumbnailPublicId(), "image");
            }
        } catch (IOException e) {
            throw new FileUploadException("Failed to delete skill image from Cloudinary: "
                    + e.getMessage(), e);
        }

        //  Database se resume delete
        projectsRepository.delete(projects);
    }


    //^ Partial Update Projects 
    @Override
    public ProjectsResponseDto updatePartialProjects(Long id, Map<String, Object> updates) {
        Projects projects = projectsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id : " + id));
        updates.forEach((fields, value) -> {
            switch (fields) {
                case "title":
                    projects.setTitle((String) value);
                    break;
                case "shortDescription":
                    projects.setShortDescription((String) value);
                    break;

                case "description":
                    projects.setDescription((String) value);
                    break;

                case "githubUrl":
                    projects.setGithubUrl((String) value);
                    break;
                case "liveUrl":
                    projects.setLiveUrl((String) value);
                    break;
                case "startDate":
                    projects.setStartDate((String) value);
                    break;
                case "endDate":
                    projects.setEndDate((String) value);
                    break;
                case "status":
                    projects.setStatus((String) value);
                    break;
                case "featured":
                    projects.setFeatured((Boolean) value);
                    break;
                case "displayOrder":
                    projects.setDisplayOrder((Integer) value);
                    break;
                default:
                    throw new BadRequestException("Field ss not supported" + fields);
            }
        });

        Projects saveProjects = projectsRepository.save(projects);
        return modelMapper.map(saveProjects, ProjectsResponseDto.class);
    }


       // ^ relation ship code my projects and profile
       private ProjectsResponseDto mapToResponse(Projects projects) {

           ProjectsResponseDto response = modelMapper.map(projects, ProjectsResponseDto.class);

           if (projects.getProfile() != null) {
               response.setProfileId(projects.getProfile().getId());
               response.setProfileName(projects.getProfile().getFullName());
           }
           return response;
       }
    


}
