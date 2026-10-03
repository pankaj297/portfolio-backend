
package com.myportfolio.backend.servicesImpl;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.myportfolio.backend.services.CloudinaryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

        private final Cloudinary cloudinary;

        @Override
        public Map<String, Object> uploadFile(MultipartFile file,String resourceType) throws IOException {

                if (file == null || file.isEmpty()) {
                        throw new IllegalArgumentException("File cannot be empty");
                }

                @SuppressWarnings("unchecked")
                Map<String, Object> result = cloudinary.uploader().upload(file.getBytes(),
                                ObjectUtils.asMap(
                                                "resource_type", resourceType,
                                                "public_id",
                                                resourceType.equals("raw")
                                                                ? UUID.randomUUID().toString() + ".pdf"
                                                                : UUID.randomUUID().toString()));

                // DEBUG
                System.out.println("========== CLOUDINARY UPLOAD ==========");
                System.out.println("Asset ID       : " + result.get("asset_id"));
                System.out.println("Public ID      : " + result.get("public_id"));
                System.out.println("Resource Type  : " + result.get("resource_type"));
                System.out.println("Format         : " + result.get("format"));
                System.out.println("Secure URL     : " + result.get("secure_url"));
                System.out.println("======================================");

                return result;
        }

        @Override
        public void deleteFile(
                        String publicId,
                        String resourceType) throws IOException {

                if (publicId == null || publicId.isBlank()) {
                        return;
                }

                cloudinary.uploader().destroy(
                                publicId,
                                ObjectUtils.asMap(
                                                "resource_type", resourceType));
        }
}






// package com.myportfolio.backend.servicesImpl;

// import java.io.IOException;
// import java.util.Map;

// import org.springframework.stereotype.Service;
// import org.springframework.web.multipart.MultipartFile;

// import com.cloudinary.Cloudinary;
// import com.cloudinary.utils.ObjectUtils;
// import com.myportfolio.backend.services.CloudinaryService;

// import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// public class CloudinaryServiceImpl implements CloudinaryService {

//         private final Cloudinary cloudinary;

//         @Override
//         public Map<String, Object> uploadFile(MultipartFile file, String resourceType) throws IOException {

//                 // CHANGE:
//                 // Validate file before sending it to Cloudinary
//                 if (file == null || file.isEmpty()) {
//                         throw new IllegalArgumentException("File cannot be empty");
//                 }

//                 return cloudinary.uploader().upload(file.getBytes(), 
//                 ObjectUtils.asMap("resource_type", resourceType));
//         }

//         @Override
//         public void deleteFile(String publicId, String resourceType) throws IOException {

//                 // CHANGE:
//                 // If no publicId is provided, don't call Cloudinary
//                 if (publicId == null || publicId.isBlank()) {
//                         return;
//                 }

//                 cloudinary.uploader().destroy(
//                                 publicId,
//                                 ObjectUtils.asMap(
//                                                 "resource_type", resourceType));
//         }
// }