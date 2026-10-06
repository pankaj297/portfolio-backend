package com.myportfolio.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;



@Entity
@Getter
@Setter
@Table(name = "my_education")
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String degree;
    private String fieldOfStudy;
    private String institution;
    private String location;
    private String startDate;
    private String endDate;
    private Boolean currentlyStudying;
    private String grade;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String institutionLogo;
    private String institutionLogoPublicId;
    
    private String institutionUrl;

      // Many Education -> One Profile
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "profile_id", nullable = false)
   private MyProfile profile;


}
