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
@Table(name = "my_experience") 
public class Experience {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
   private String companyName;
   private String position;
   private String employmentType;
   private String location;
   private String startDate;
   private String endDate;
   private Boolean currentlyWorking;
   
   private String companyLogo;
   private String companyLogoPublicId;

   private String companyUrl;

   @Column(columnDefinition = "TEXT")
   private String description;

   private Integer displayOrder;


}
