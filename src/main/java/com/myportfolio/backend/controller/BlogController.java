package com.myportfolio.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myportfolio.backend.dto.BlogRequestDto;
import com.myportfolio.backend.dto.BlogResponseDto;
import com.myportfolio.backend.services.BlogServices;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blog")
@Tag(name = "Blog APIs", description = "Blog - Create , Update, Partial Update, Get and Delete ")
public class BlogController {

    private final BlogServices blogServices;

    @GetMapping
    public ResponseEntity<List<BlogResponseDto>> getAllBlogs() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(blogServices.getAllBlogs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogResponseDto> getBlogById(
            @PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(blogServices.getBlogById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BlogResponseDto> createBlog(
            @Valid @ModelAttribute  BlogRequestDto blogRequestDto) {

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(blogServices.createBlog(blogRequestDto));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BlogResponseDto> updateBlog(
            @PathVariable Long id,
            @Valid @ModelAttribute  BlogRequestDto blogRequestDto) {

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(blogServices.updateBlog(id, blogRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(
            @PathVariable Long id) {

        blogServices.deleteBlogById(id);
        return ResponseEntity.noContent().build();
    }
}