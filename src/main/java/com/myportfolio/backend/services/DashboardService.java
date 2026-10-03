package com.myportfolio.backend.services;

import org.springframework.stereotype.Service;

import com.myportfolio.backend.dto.DashboardResponseDto;
import com.myportfolio.backend.repository.AchievementsRepository;
import com.myportfolio.backend.repository.BlogRepository;
import com.myportfolio.backend.repository.CertificationsRepository;
import com.myportfolio.backend.repository.ContactRepository;
import com.myportfolio.backend.repository.EducationRepository;
import com.myportfolio.backend.repository.ExperienceRepository;
import com.myportfolio.backend.repository.MyServicesRepository;
import com.myportfolio.backend.repository.MySkillsRepository;
import com.myportfolio.backend.repository.ProjectsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ProjectsRepository projectsRepository;
    private final MySkillsRepository mySkillsRepository;
    private final ExperienceRepository experienceRepository;
    private final EducationRepository educationRepository;
    private final CertificationsRepository certificationsRepository;
    private final AchievementsRepository achievementsRepository;
    private final MyServicesRepository myServicesRepository;
    private final ContactRepository contactRepository;
    private final BlogRepository blogRepository;

    public DashboardResponseDto getDashboard() {

        Long projects = projectsRepository.count();
        Long mySkills = mySkillsRepository.count();
        Long experience = experienceRepository.count();
        Long education = educationRepository.count();
        Long certification = certificationsRepository.count();
        Long achievements = achievementsRepository.count();
        Long myServices = myServicesRepository.count();
        Long contact = contactRepository.count();
        Long blog = blogRepository.count();

        return new DashboardResponseDto(
                projects,
                mySkills,
                experience,
                education,
                certification,
                achievements,
                myServices,
                contact,                                             
                blog

        );
    }

}
