package com.dspassov.kovapi.repositories;

import com.dspassov.kovapi.areas.game.entities.Hero;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroRepository extends PagingAndSortingRepository<Hero, String> {

    Hero findByName(String name);

    @Query("SELECT h FROM Hero h WHERE h.user.username = :username")
    Hero findByUser(@Param("username") String username);

    List<Hero> findTop10ByOrderByLevelDescExperienceDesc();
}
