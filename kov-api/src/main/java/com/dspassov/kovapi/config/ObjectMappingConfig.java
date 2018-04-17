package com.dspassov.kovapi.config;


import com.dspassov.kovapi.areas.game.entities.BattleSet;
import com.dspassov.kovapi.areas.game.models.view.BattleSetViewModel;
import com.dspassov.kovapi.areas.game.models.view.ItemViewModel;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import org.modelmapper.spi.MappingContext;
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

//        Converter<BattleSet, BattleSetViewModel> battleSetBattleSetViewModelConverter = mappingContext -> {
//
//            BattleSet source = mappingContext.getSource();
//            BattleSetViewModel destination = mappingContext.getDestination();
//
//
//            destination.setSword(new ItemViewModel());
//            destination.setShield(new ItemViewModel());
//            destination.setArmor(new ItemViewModel());
//
//            return destination;
//        };
//        mapper.addConverter(battleSetBattleSetViewModelConverter);
    }

}
