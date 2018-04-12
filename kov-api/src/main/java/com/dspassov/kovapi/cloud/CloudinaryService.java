package com.dspassov.kovapi.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService implements CloudService {

    private final Cloudinary cloudinary;


    @Autowired
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String saveImage(MultipartFile image) throws IOException {
        Map upload = this.cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
        String imageUrl = (String) upload.get("secure_url");
        return imageUrl;
    }

    @Async
    public void deleteImage(String imageId) throws IOException {
        this.cloudinary.uploader().destroy(imageId, ObjectUtils.emptyMap());
    }
}
