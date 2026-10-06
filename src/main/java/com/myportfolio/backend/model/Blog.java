package com.myportfolio.backend.model;

import java.time.LocalDateTime;

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
@Table(name = "my_blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String slug;

    @Column(length = 500)
    private String excerpt;

    @Column(columnDefinition = "TEXT")
    private String content;
    
    private String imgThumbnail;
    private String imgThumbnailPublicId;

    
    private String category;
    private Boolean published;
    private LocalDateTime publishedAt;

     // Many Blog -> One Profile
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "profile_id", nullable = false)
   private MyProfile profile;

   
}

