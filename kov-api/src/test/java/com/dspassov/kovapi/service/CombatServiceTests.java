package com.dspassov.kovapi.service;

import com.dspassov.kovapi.areas.game.entities.Hero;
import com.dspassov.kovapi.areas.game.models.service.HeroCombatServiceModel;
import com.dspassov.kovapi.areas.game.models.view.BattleSetViewModel;
import com.dspassov.kovapi.areas.game.models.view.NeutralUnitViewModel;
import com.dspassov.kovapi.areas.game.services.CombatServiceImpl;
import com.dspassov.kovapi.areas.game.services.HeroJobService;
import com.dspassov.kovapi.areas.game.services.HeroService;
import com.dspassov.kovapi.areas.game.services.NeutralUnitService;
import com.dspassov.kovapi.exceptions.HeroFightException;
import com.dspassov.kovapi.web.ResponseMessageConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CombatServiceTests {

    private static final String HERO_NAME = "Arthas";

    @Mock
    private HeroService heroService;

    @Mock
    private NeutralUnitService neutralUnitService;

    @Mock
    private HeroJobService heroJobService;

    private CombatServiceImpl combatService;

    @Before
    public void init() {
        this.combatService = new CombatServiceImpl(
                this.heroService, this.neutralUnitService, this.heroJobService);

        when(this.heroJobService.isAtWork())
                .thenAnswer(a -> false);

        when(this.heroService.isHeroReadyToFight())
                .thenAnswer(a -> true);
    }

    @Test
    public void testHeroFightWithNeutral_fightWithWeakUnit_expectWinMessage() {

        //arrange
        when(this.heroService.getHeroCombatModel())
                .thenAnswer(a -> this.getHero(10));
        when(this.neutralUnitService.getUnitById(any()))
                .thenAnswer(a -> this.getNeutral(1));

        //act
        String actual = this.combatService.heroFightWithNeutral("test");
        String expected = String.format(ResponseMessageConstants.FIGHT_WON, HERO_NAME);

        assertEquals(
                "Incorrect result of fight",
                expected,
                actual
        );
    }

    @Test
    public void testHeroFightWithNeutral_fightWithEquallyStrongUnit_expectDrawMessage() {

        //arrange
        when(this.heroService.getHeroCombatModel())
                .thenAnswer(a -> this.getHero(1));
        when(this.neutralUnitService.getUnitById(any()))
                .thenAnswer(a -> this.getNeutral(1));

        //act
        String actual = this.combatService.heroFightWithNeutral("test");
        String expected = String.format(ResponseMessageConstants.FIGHT_DRAWN, HERO_NAME);

        assertEquals(
                "Incorrect result of fight",
                expected,
                actual
        );
    }

    @Test
    public void testHeroFightWithNeutral_fightWithStrongerUnit_expectLossMessage() {

        //arrange
        when(this.heroService.getHeroCombatModel())
                .thenAnswer(a -> this.getHero(1));
        when(this.neutralUnitService.getUnitById(any()))
                .thenAnswer(a -> this.getNeutral(10));

        //act
        String actual = this.combatService.heroFightWithNeutral("test");
        String expected = String.format(ResponseMessageConstants.FIGHT_LOST, HERO_NAME);

        assertEquals(
                "Incorrect result of fight",
                expected,
                actual
        );
    }

    @Test
    public void testHeroFightOnArena_fightWithWeakerEnemy_expectWinMessage() {

        //arrange
        HeroCombatServiceModel current = this.getHero(10);
        current.setId("current");
        HeroCombatServiceModel enemy = this.getHero(1);
        enemy.setId("enemy");

        when(this.heroService.getHeroCombatModel())
                .thenAnswer(a -> current);
        when(this.heroService.getHeroById("test"))
                .thenAnswer(a -> enemy);

        //act
        String actual = this.combatService.heroFightOnArena("test");
        String expected = String.format(ResponseMessageConstants.FIGHT_WON, HERO_NAME);

        assertEquals(
                "Incorrect result of fight",
                expected,
                actual
        );
    }

    @Test
    public void testHeroFightOnArena_fightWithEquallyStrongEnemy_expectDrawMessage() {

        //arrange
        HeroCombatServiceModel current = this.getHero(10);
        current.setId("current");
        HeroCombatServiceModel enemy = this.getHero(10);
        enemy.setId("enemy");

        when(this.heroService.getHeroCombatModel())
                .thenAnswer(a -> current);
        when(this.heroService.getHeroById("test"))
                .thenAnswer(a -> enemy);

        //act
        String actual = this.combatService.heroFightOnArena("test");
        String expected = String.format(ResponseMessageConstants.FIGHT_DRAWN, HERO_NAME);

        assertEquals(
                "Incorrect result of fight",
                expected,
                actual
        );
    }

    @Test
    public void testHeroFightOnArena_fightWithStrongerEnemy_expectLossMessage() {

        //arrange
        HeroCombatServiceModel current = this.getHero(1);
        current.setId("current");
        HeroCombatServiceModel enemy = this.getHero(10);
        enemy.setId("enemy");

        when(this.heroService.getHeroCombatModel())
                .thenAnswer(a -> current);
        when(this.heroService.getHeroById("test"))
                .thenAnswer(a -> enemy);

        //act
        String actual = this.combatService.heroFightOnArena("test");
        String expected = String.format(ResponseMessageConstants.FIGHT_LOST, HERO_NAME);

        assertEquals(
                "Incorrect result of fight",
                expected,
                actual
        );
    }

    @Test
    public void testHeroFightOnArena_fightWithTheSameHero_shouldThrowException() {

        //arrange
        HeroCombatServiceModel current = this.getHero(1);
        current.setId("current");

        when(this.heroService.getHeroCombatModel())
                .thenAnswer(a -> current);
        when(this.heroService.getHeroById("test"))
                .thenAnswer(a -> current);

        try {
            //act
            this.combatService.heroFightOnArena("test");
            fail("Expected HeroFightException");
        } catch (HeroFightException hfe) {

            //assert
            assertEquals(
                    "Incorrect exception message",
                    ResponseMessageConstants.CANNOT_TARGET_HIMSELF,
                    hfe.getMessage()
            );
        }
    }

    private NeutralUnitViewModel getNeutral(int statParam) {
        NeutralUnitViewModel neutral = new NeutralUnitViewModel();
        neutral.setLevel(statParam);
        neutral.setHealth(statParam);
        neutral.setStamina(statParam);
        neutral.setStrength(statParam);
        neutral.setDefense(statParam);
        neutral.setLootGold(statParam);
        return neutral;
    }

    private HeroCombatServiceModel getHero(int statParam) {
        HeroCombatServiceModel hero = new HeroCombatServiceModel();
        BattleSetViewModel bs = new BattleSetViewModel();
        hero.setBattleSet(bs);
        hero.setName(HERO_NAME);
        hero.setLevel(statParam);
        hero.setStrength(statParam);
        hero.setStamina(statParam);
        hero.setDefense(statParam);
        hero.setHealth(statParam);
        return hero;
    }
}
