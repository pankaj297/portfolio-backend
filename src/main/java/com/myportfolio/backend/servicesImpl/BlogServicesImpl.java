package com.myportfolio.backend.servicesImpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.myportfolio.backend.dto.BlogRequestDto;
import com.myportfolio.backend.dto.BlogResponseDto;
import com.myportfolio.backend.exception.FileUploadException;
import com.myportfolio.backend.exception.ResourceNotFoundException;
import com.myportfolio.backend.model.Blog;
import com.myportfolio.backend.repository.BlogRepository;
import com.myportfolio.backend.services.BlogServices;
import com.myportfolio.backend.services.CloudinaryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogServicesImpl implements BlogServices {

    private final BlogRepository blogRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;

    @Override
    public List<BlogResponseDto> getAllBlogs() {
        List<Blog> blogs = blogRepository.findAll();
        return blogs.stream().map(blog -> modelMapper.map(blog, BlogResponseDto.class)).toList();
    }

    @Override
    public BlogResponseDto getBlogById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog id not found with id : " + id));
        return modelMapper.map(blog, BlogResponseDto.class);
    }

    @Override
    public BlogResponseDto createBlog(BlogRequestDto blogRequestDto) {
        Blog blog = modelMapper.map(blogRequestDto, Blog.class);

        if (Boolean.TRUE.equals(blog.getPublished())) {
            blog.setPublishedAt(LocalDateTime.now());
        }

        try {
            if (blogRequestDto.getImgThumbnail() != null
                    && !blogRequestDto.getImgThumbnail().isEmpty()) {

                Map<String, Object> result = cloudinaryService.uploadFile(blogRequestDto.getImgThumbnail(),
                        "image");
                blog.setImgThumbnail((String) result.get("secure_url"));
                blog.setImgThumbnailPublicId((String) result.get("public_id"));
            }
        } catch (IOException e) {
            throw new FileUploadException("Failed to upload blog Image", e);
        }

        Blog savedBlog = blogRepository.save(blog);
        return modelMapper.map(savedBlog, BlogResponseDto.class);
    }

    @Override
    public BlogResponseDto updateBlog(Long id, BlogRequestDto blogRequestDto) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog id not found with id : " + id));
        //   modelMapper.map(blogRequestDto, blog);
        if (Boolean.TRUE.equals(blog.getPublished())
                && blog.getPublishedAt() == null) {
            blog.setPublishedAt(LocalDateTime.now());
        }

        blog.setTitle(blogRequestDto.getTitle());
        blog.setSlug(blogRequestDto.getSlug());
        blog.setExcerpt(blogRequestDto.getExcerpt());
        blog.setContent(blogRequestDto.getContent());
        blog.setCategory(blogRequestDto.getCategory());
        blog.setPublished(blogRequestDto.getPublished());

        try {
            if (blogRequestDto.getImgThumbnail() != null
                    && !blogRequestDto.getImgThumbnail().isEmpty()) {

                if (blog.getImgThumbnailPublicId() != null && !blog.getImgThumbnailPublicId().isBlank()) {
                    cloudinaryService.deleteFile(blog.getImgThumbnailPublicId(), "image");
                }

                Map<String, Object> result = cloudinaryService.uploadFile(blogRequestDto.getImgThumbnail(),
                        "image");
                blog.setImgThumbnail((String) result.get("secure_url"));
                blog.setImgThumbnailPublicId((String) result.get("public_id"));
            }
        } catch (IOException e) {
            throw new FileUploadException("Failed to upload blog Image", e);
        }

        Blog savedBlog = blogRepository.save(blog);
        return modelMapper.map(savedBlog, BlogResponseDto.class);
    }

    @Override
    public void deleteBlogById(Long id) {

        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog id not found with id : " + id));

        try {
            if (blog.getImgThumbnailPublicId() != null && !blog.getImgThumbnailPublicId().isBlank()) {
                cloudinaryService.deleteFile(blog.getImgThumbnailPublicId(), "image");
            }
        } catch (IOException e) {
            throw new FileUploadException("Failed to delete blog Image", e);
        }
        blogRepository.delete(blog);
    }

}