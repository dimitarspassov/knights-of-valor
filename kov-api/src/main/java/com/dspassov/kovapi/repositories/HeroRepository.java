package com.dspassov.kovapi.repositories;

import com.dspassov.kovapi.areas.game.entities.Hero;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRepository extends PagingAndSortingRepository<Hero, String> {

    Hero findByName(String name);

}
