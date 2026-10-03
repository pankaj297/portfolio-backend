package com.myportfolio.backend.services;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

    Map<String, Object> uploadFile(MultipartFile file, String resourceType) throws IOException;

    void deleteFile(String publicId, String resourceType) throws IOException;
}