package com.myportfolio.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

}
