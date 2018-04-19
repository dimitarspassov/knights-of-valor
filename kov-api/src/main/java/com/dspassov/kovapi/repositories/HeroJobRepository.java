package com.dspassov.kovapi.repositories;

import com.dspassov.kovapi.areas.game.entities.Hero;
import com.dspassov.kovapi.areas.game.entities.HeroJob;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface HeroJobRepository extends JpaRepository<HeroJob, String> {

    HeroJob findByHero(Hero hero);

    @Transactional
    void deleteById(String id);
}
