package com.myportfolio.backend.services;

import java.util.List;

import com.myportfolio.backend.dto.BlogRequestDto;
import com.myportfolio.backend.dto.BlogResponseDto;

public interface BlogServices {

    List<BlogResponseDto> getAllBlogs();

    BlogResponseDto getBlogById(Long id);

    BlogResponseDto createBlog(BlogRequestDto blogRequestDto);

    BlogResponseDto updateBlog(Long id,BlogRequestDto blogRequestDto);

    void deleteBlogById(Long id);
}