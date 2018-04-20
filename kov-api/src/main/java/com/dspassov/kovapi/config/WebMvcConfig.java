package com.dspassov.kovapi.config;

import com.dspassov.kovapi.interceptors.QueryStringSanitizeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final QueryStringSanitizeInterceptor queryStringSanitizer;

    @Autowired
    public WebMvcConfig(QueryStringSanitizeInterceptor inputSanitizer) {
        this.queryStringSanitizer = inputSanitizer;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.queryStringSanitizer);
    }
}
