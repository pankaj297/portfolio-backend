package com.myportfolio.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myportfolio.backend.dto.ProjectsRequestDto;
import com.myportfolio.backend.dto.ProjectsResponseDto;
import com.myportfolio.backend.services.ProjectsServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor 
@RequestMapping("/api/projects")
public class ProjectsController {
    
    public final ProjectsServices projectsServices;

    //^ Get All Project 
    @GetMapping
    public ResponseEntity<List<ProjectsResponseDto>> getAllProjects() {
        return ResponseEntity.status(HttpStatus.OK).body(projectsServices.getAllProjects());
    }

    //^ Get Project with id
    @GetMapping("/{id}")
    public ResponseEntity<ProjectsResponseDto> getProjectById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(projectsServices.getProjectById(id));
    }

    //^ Create Projects
    @PostMapping
    public ResponseEntity<ProjectsResponseDto> createProjects(@Valid @ModelAttribute  ProjectsRequestDto projectsRequestDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(projectsServices.createProjects(projectsRequestDto));

    }

    //^ Update Projects
    @PutMapping("/{id}")
    public ResponseEntity<ProjectsResponseDto> updateProjects(@PathVariable Long id,
         @Valid  @ModelAttribute  ProjectsRequestDto projectsRequestDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(projectsServices.updateProjects(id, projectsRequestDto));
    }

    //^ Delete Projects 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectsServices.deleteProjectById(id);
        return ResponseEntity.noContent().build();
    }

    //^ Partial Update
    @PatchMapping("/{id}")
    public ResponseEntity<ProjectsResponseDto> updatePartialProjects(@PathVariable  Long id, @RequestBody  Map<String, Object> updates){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(projectsServices.updatePartialProjects(id, updates));
    }


}
