package com.myportfolio.backend.model;



import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "my_profile")
public class MyProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    
    private String profileImg;

    // Cloudinary public ID
    private String profileImgPublicId;

    private String headline;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String resumeUrl;

    // Cloudinary resume public ID
    private String resumePublicId;


    private String location;

    @Column(unique = true, nullable = false)
    private String email;
    
    private String phone;
    private String githubUrl;
    private String linkedinUrl;
    private String portfolioUrl;
    private String leedcodeUrl;
    private boolean availableForWork;

     //^One Profile -> Many Certifications
    @OneToMany(
        mappedBy = "profile",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private List<Certifications> certifications = new ArrayList<>();

    //^ One Profile -> Many Education
    @OneToMany(mappedBy = "profile",
             cascade = CascadeType.ALL,
                      orphanRemoval = true, 
                              fetch = FetchType.LAZY)
    private List<Education> education = new ArrayList<>();

    // ^ One Profile -> Many Skills
    @OneToMany(mappedBy = "profile", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true, 
                    fetch = FetchType.LAZY)
    private List<MySkills> skills = new ArrayList<>();


    // ^ One Profile -> Many Services
    @OneToMany(mappedBy = "profile", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true, 
                               fetch = FetchType.LAZY)
    private List<MyServices> services = new ArrayList<>();

    // ^ One Profile -> Many Experience
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Experience> experience = new ArrayList<>();

    // ^ One Profile -> Many Projects
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Projects> projects = new ArrayList<>();

    // ^ One Profile -> Many Achievements
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Achievements> achievements = new ArrayList<>();

    // ^ One Profile -> Many Blog
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Blog> blog = new ArrayList<>();

    // ^ One Profile -> Many Resume
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Resume> resumes = new ArrayList<>();
    
}

// orphanRemoval = true
// अगर कोई Certification Profile की list से remove कर दी गई और उसका कोई parent Profile नहीं रहा, तो database से भी वह Certification delete हो जाएगी।