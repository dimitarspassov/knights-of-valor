package com.dspassov.kovapi.repository;

import com.dspassov.kovapi.areas.game.entities.BattleSet;
import com.dspassov.kovapi.areas.game.entities.Hero;
import com.dspassov.kovapi.areas.game.entities.Inventory;
import com.dspassov.kovapi.areas.users.entities.User;
import com.dspassov.kovapi.repositories.HeroRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@ActiveProfiles("test")
public class HeroRepositoryTests {

    private static final String USERNAME = "testUser@test.com";
    private static final String HERO_NAME = "Arthas";

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private HeroRepository heroRepository;


    @Before
    public void init() {

        //arrange
        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword("12341234");

        Hero hero = new Hero();
        hero.setName(HERO_NAME);
        hero.setBattleSet(new BattleSet());
        hero.setInventory(new Inventory());

        user.setHero(hero);

        this.testEntityManager.persistAndFlush(hero);
        this.testEntityManager.persistAndFlush(user);


    }

    @Test
    public void testFindByUser_givenUsername_shouldReturnTheTargetHero() {

        //act
        Hero hero = this.heroRepository.findByUser(USERNAME);

        //assert
        assertEquals(
                "Hero name should be equal to " + HERO_NAME,
                HERO_NAME,
                hero.getName()
        );
    }

    @Test
    public void testFindByUser_nonExistentUsername_shouldReturnNull() {

        //act
        Hero hero = this.heroRepository.findByUser(USERNAME + "a");

        //assert
        assertNull("Hero should be null", hero);
    }
}
