package com.dspassov.kovapi.cloud;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudService {

    String saveImage(MultipartFile image) throws IOException;

    void deleteImage(String imageId) throws IOException;

}
