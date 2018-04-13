package com.dspassov.kovapi.config;


import org.modelmapper.ModelMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ObjectMappingConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        //this.defineTypeMaps(mapper);
        return mapper;
    }

    private void defineTypeMaps(ModelMapper mapper) {

        // todo
    }

}
