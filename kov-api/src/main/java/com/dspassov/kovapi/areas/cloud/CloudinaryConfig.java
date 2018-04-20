package com.dspassov.kovapi.areas.cloud;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {


    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "knights-of-valor",
                "api_key", "945789634444629",
                "api_secret", "eyb-is_5ecGf5STJuD9uTnTuo5Y"));
        return cloudinary;
    }
}
